## How to use these script?

```
certbot certonly --manual --preferred-challenges=dns --manual-auth-hook /path/to/dns/authenticator.sh --manual-cleanup-hook /path/to/dns/cleanup.sh -d secure.example.com
```

### For more information:
> https://eff-certbot.readthedocs.io/en/stable/using.html#hooks

NOTE: Scripts for the GoDaddy domain registrar are tested, but for cloudflare, scripts are not yet tested.