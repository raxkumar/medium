package terraform.backend.mongodb.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import terraform.backend.mongodb.service.HttpBackendConfigurationService;

import java.util.Objects;

@RestController
@RequestMapping("/terraform-state")
public class HttpBackendConfigurationController {

    @Autowired
    private HttpBackendConfigurationService httpBackendConfigurationService;
    private static final Logger log = LoggerFactory.getLogger(HttpBackendConfigurationController.class);

    @GetMapping
    public String getState(@RequestParam String key) {
        log.info("----------------------------------GET STATE----------------------------------------------");
        return httpBackendConfigurationService.getState(key);
    }

    @PostMapping
    public String uploadState(@RequestParam String key, @RequestBody String state) {
        log.info("----------------------------------UPLOAD STATE----------------------------------------------");
        return httpBackendConfigurationService.uploadState(key,state);
    }

    @RequestMapping("/lock")
    public ResponseEntity<String> lockState(@RequestParam String key, @RequestBody String lockInfo) {
        log.info("----------------------------------LOCK STATE----------------------------------------------");
        String holdingLockInfo = httpBackendConfigurationService.getHoldingLockInfo(key);
        if (Objects.nonNull(holdingLockInfo)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("State is already locked by " + holdingLockInfo);
        }
        holdingLockInfo = httpBackendConfigurationService.lockState(key, lockInfo);
        return ResponseEntity.ok("State locked by " + holdingLockInfo);
    }

    @RequestMapping("/unlock")
    public ResponseEntity<String> unlockState(@RequestParam String key, @RequestBody String lockInfo) {
        log.info("----------------------------------UNLOCK STATE----------------------------------------------");
        String holdingLockInfo = httpBackendConfigurationService.getHoldingLockInfo(key);
        if (Objects.isNull(holdingLockInfo) || !holdingLockInfo.equals(lockInfo)) {
            return ResponseEntity.status(HttpStatus.LOCKED).body("State is not locked by " + lockInfo);
        }
        httpBackendConfigurationService.unlockState(key);
        return ResponseEntity.ok("State unlocked by " + lockInfo);
    }
}
