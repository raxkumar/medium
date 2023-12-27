package terraform.backend.mongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import terraform.backend.mongodb.model.StateConfig;
import terraform.backend.mongodb.repository.HttpBackendConfigurationRepo;

import java.util.Optional;

@Service
public class HttpBackendConfigurationService {

    @Autowired
    private HttpBackendConfigurationRepo httpBackendConfigurationRepo;

    public String getState(String key){
        Optional<StateConfig> optionalState = httpBackendConfigurationRepo.findById(key);
        return optionalState.isPresent() ? optionalState.get().getState() : "";
    }

    public String uploadState(String key, String state){
        Optional<StateConfig> optionalState = httpBackendConfigurationRepo.findById(key);
        if(optionalState.isEmpty()){
            return null;
        }
        StateConfig stateConfig = new StateConfig();
        stateConfig.setKey(key);
        stateConfig.setState(state);
        stateConfig.setHoldingLockInfo(optionalState.get().getHoldingLockInfo());
        stateConfig = httpBackendConfigurationRepo.save(stateConfig);
        return stateConfig.getState();
    }

    public String lockState(String key, String lockInfo) {
        Optional<StateConfig> optionalState = httpBackendConfigurationRepo.findById(key);
        StateConfig stateConfig = new StateConfig();
        stateConfig.setKey(key);
        stateConfig.setHoldingLockInfo(lockInfo);
        if(optionalState.isPresent()){
            stateConfig.setState(optionalState.get().getState());
        }
        stateConfig = httpBackendConfigurationRepo.save(stateConfig);
        return stateConfig.getHoldingLockInfo();
    }

    public String getHoldingLockInfo(String key) {
        Optional<StateConfig> optionalState = httpBackendConfigurationRepo.findById(key);
        return optionalState.isPresent() ? optionalState.get().getHoldingLockInfo() : null;
    }

    public void unlockState(String key) {
        Optional<StateConfig> optionalState = httpBackendConfigurationRepo.findById(key);
        if(optionalState.isPresent()) {
            StateConfig stateConfig = new StateConfig();
            stateConfig.setKey(key);
            stateConfig.setState(optionalState.get().getState());
            stateConfig.setHoldingLockInfo(null);
            httpBackendConfigurationRepo.save(stateConfig);
        }
    }
}
