#!/bin/bash

# Set your GoDaddy API credentials
API_KEY="YOUR_API_KEY"
API_SECRET="YOUR_API_SECRET"
DOMAIN="your-domain" 


# Function to get Domain ID
get_domain_id() {
    curl -s -X GET -H "Authorization: sso-key $API_KEY:$API_SECRET" \
    "https://api.godaddy.com/v1/domains/$DOMAIN" | jq -r '.domainId'
}


# Get Domain ID
DOMAIN_ID=$(get_domain_id)

if [ -z "$DOMAIN_ID" ]; then
    echo "Domain not found."
    exit 1
fi

echo "Domain exists with Domain ID: ${DOMAIN_ID}"

# Capture the subdomain part of CERTBOT_DOMAIN
SUBDOMAIN=$(echo "$CERTBOT_DOMAIN" | sed "s/\.$DOMAIN//")

# Create TXT record without root domain
CREATE_DOMAIN="_acme-challenge.$SUBDOMAIN"

curl_response=$(curl -s -X PUT -H "Authorization: sso-key $API_KEY:$API_SECRET" \
"https://api.godaddy.com/v1/domains/$DOMAIN/records/TXT/$CREATE_DOMAIN" \
-H "Content-Type: application/json" --data '[{"data":"'$CERTBOT_VALIDATION'","ttl":600}]')

# Check if curl response contains error
if echo "$curl_response" | grep -q '"code":"'; then
    echo "Error: Failed to create TXT record."
    echo "Error details: $curl_response"
    exit 1
else
    echo "TXT record created successfully."
fi

# Sleep to make sure the change has time to propagate over to DNS
sleep 25
