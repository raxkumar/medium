package terraform.backend.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StateConfig {
    @Id
    private String key;

    private String state;

    private String holdingLockInfo;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHoldingLockInfo() {
        return holdingLockInfo;
    }

    public void setHoldingLockInfo(String holdingLockInfo) {
        this.holdingLockInfo = holdingLockInfo;
    }
}
