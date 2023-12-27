package terraform.backend.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import terraform.backend.mongodb.model.StateConfig;

@Repository
public interface HttpBackendConfigurationRepo extends MongoRepository<StateConfig,String> {

}
