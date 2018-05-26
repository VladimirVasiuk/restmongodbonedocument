package com.solution.restmongodbonedocument;

import com.solution.restmongodbonedocument.model.entity.RetailerGroup;
import com.solution.restmongodbonedocument.model.entity.RetailerIntegration;
import com.solution.restmongodbonedocument.model.entity.RetailerProfile;
import com.solution.restmongodbonedocument.service.RetailerIntegrationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DbFiller implements CommandLineRunner {

    private RetailerIntegrationsService retailerIntegrationsService;

    @Autowired
    public DbFiller(RetailerIntegrationsService retailerIntegrationsService) {
        this.retailerIntegrationsService = retailerIntegrationsService;
    }

    @Override
    public void run(String... args) throws Exception {
        RetailerIntegration firstIntegration = new RetailerIntegration("1st Integration", "about 1st integration...");
        RetailerIntegration secondIntegration = new RetailerIntegration("2nd Integration", "about 2nd integration...");
        RetailerIntegration thirdIntegration = new RetailerIntegration("3rd Integration", "about 3rd integration...");

        retailerIntegrationsService.deleteAll();
        firstIntegration = retailerIntegrationsService.insertRetailerIntegration(firstIntegration);
        secondIntegration = retailerIntegrationsService.insertRetailerIntegration(secondIntegration);
        retailerIntegrationsService.insertRetailerIntegration(thirdIntegration);
        Optional<RetailerGroup> retailerGroupOptional;
        retailerIntegrationsService.insertRetailerGroup(
                new RetailerGroup("101st group", "101st group of 1st integration"),
                firstIntegration.getId());
        retailerGroupOptional = retailerIntegrationsService.insertRetailerGroup(
                new RetailerGroup("201st group", "201st group of 2nd integration"),
                secondIntegration.getId());
        if (retailerGroupOptional.isPresent()) {
            retailerIntegrationsService.insertRetailerProfile(
                    new RetailerProfile("1st profile (2-201)", "1st description"),
                    secondIntegration.getId(),
                    retailerGroupOptional.get().getId());

        }
    }
}
