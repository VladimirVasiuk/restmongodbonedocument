package com.solution.restmongodbonedocument.model.repository;

import com.solution.restmongodbonedocument.model.entity.RetailerIntegration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailerIntegrationRepository extends MongoRepository<RetailerIntegration, String> {

}
