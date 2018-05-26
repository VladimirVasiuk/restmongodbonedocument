package com.solution.restmongodbonedocument.web;

import com.solution.restmongodbonedocument.model.entity.RetailerGroup;
import com.solution.restmongodbonedocument.model.entity.RetailerIntegration;
import com.solution.restmongodbonedocument.model.entity.RetailerProfile;
import com.solution.restmongodbonedocument.service.RetailerIntegrationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class RetailerIntegrationsServiceController {

    private RetailerIntegrationsService retailerIntegrationsService;

    @Autowired
    public RetailerIntegrationsServiceController(RetailerIntegrationsService retailerIntegrationsService) {
        this.retailerIntegrationsService = retailerIntegrationsService;
    }

    @PostMapping("/retailerIntegrations")
    public RetailerIntegration insertRetailerIntegration(@RequestBody RetailerIntegration retailerIntegration) {
        return retailerIntegrationsService.insertRetailerIntegration(retailerIntegration);
    }

    @GetMapping("/retailerIntegrations")
    public List<RetailerIntegration> getAllRetailerIntegration() {
        return retailerIntegrationsService.getAllRetailerIntegration();
    }

    @GetMapping("/retailerIntegrations/{retailerIntegrationId}")
    public RetailerIntegration getRetailerIntegrationById(@PathVariable("retailerIntegrationId") String retailerIntegrationId) {
        Optional<RetailerIntegration> optional = retailerIntegrationsService.getRetailerIntegrationById(retailerIntegrationId);
        if (optional.isPresent())
            return optional.get();
        throw new ResourceNotFoundException();
    }

    @PutMapping("/retailerIntegrations/{retailerIntegrationId}")
    public RetailerIntegration updateRetailerIntegration(@RequestBody RetailerIntegration retailerIntegration,
                                                         @PathVariable("retailerIntegrationId") String retailerIntegrationId) {
        return retailerIntegrationsService.updateRetailerIntegration(retailerIntegration, retailerIntegrationId);
    }

    @DeleteMapping("/retailerIntegrations/{retailerIntegrationId}")
    public void deleteRetailerIntegration(@PathVariable("retailerIntegrationId") String id) {
        retailerIntegrationsService.deleteRetailerIntegrationById(id);
    }

    @GetMapping("/retailerIntegrations/{retailerIntegrationId}/groups")
    public List<RetailerGroup> getAllRetailerGroupByRetailerIntegrationId(
            @PathVariable("retailerIntegrationId") String retailerIntegrationId) {
        return retailerIntegrationsService.getAllRetailerGroupByRetailerIntegrationId(retailerIntegrationId);
    }

    @PostMapping("/retailerIntegrations/{retailerIntegrationId}/groups")
    public RetailerGroup insertRetailerGroup(@RequestBody RetailerGroup retailerGroup,
                                             @PathVariable("retailerIntegrationId") String retailerIntegrationId) {
        Optional<RetailerGroup> optional = retailerIntegrationsService.
                insertRetailerGroup(retailerGroup, retailerIntegrationId);
        if (optional.isPresent())
            return optional.get();
        throw new ResourceNotFoundException();
    }

    @GetMapping("/retailerIntegrations/{retailerIntegrationId}/groups/{groupId}")
    public RetailerGroup getRetailerGroupById(@PathVariable("retailerIntegrationId") String retailerIntegrationId,
                                              @PathVariable("groupId") String groupId) {
        Optional<RetailerGroup> optional = retailerIntegrationsService.getRetailerGroupById(retailerIntegrationId, groupId);
        if (optional.isPresent())
            return optional.get();
        throw new ResourceNotFoundException();
    }


    @PutMapping("/retailerIntegrations/{retailerIntegrationId}/groups/{groupId}")
    public RetailerGroup updateRetailerGroup(@RequestBody RetailerGroup retailerGroup,
                                             @PathVariable("retailerIntegrationId") String retailerIntegrationId,
                                             @PathVariable("groupId") String groupId) {
        Optional<RetailerGroup> optional = retailerIntegrationsService.
                updateRetailerGroup(retailerGroup, retailerIntegrationId, groupId);
        if (optional.isPresent())
            return optional.get();
        throw new ResourceNotFoundException();

    }

    @DeleteMapping("/retailerIntegrations/{retailerIntegrationId}/groups/{groupId}")
    public void deleteRetailerGroup(@PathVariable("retailerIntegrationId") String retailerIntegrationId,
                                    @PathVariable("groupId") String groupId) {
        retailerIntegrationsService.deleteRetailerGroup(retailerIntegrationId, groupId);
    }

    @GetMapping("/retailerIntegrations/{retailerIntegrationId}/groups/{groupId}/profile")
    public List<RetailerProfile> getAllRetailerProfileByRetailerIntegrationId(
            @PathVariable("retailerIntegrationId") String retailerIntegrationId,
            @PathVariable("groupId") String groupId) {
        return retailerIntegrationsService.getAllRetailerProfile(retailerIntegrationId, groupId);
    }

    @PostMapping("/retailerIntegrations/{retailerIntegrationId}/groups/{groupId}/profile")
    public RetailerProfile insertRetailerProfile(@RequestBody RetailerProfile retailerProfile,
                                                 @PathVariable("retailerIntegrationId") String retailerIntegrationId,
                                                 @PathVariable("groupId") String groupId) {
        Optional<RetailerProfile> retailerProfileOptional = retailerIntegrationsService.insertRetailerProfile(
                retailerProfile, retailerIntegrationId, groupId);
        if (retailerProfileOptional.isPresent())
            return retailerProfileOptional.get();
        throw new ResourceNotFoundException();
    }

    @GetMapping("/retailerIntegrations/{retailerIntegrationId}/groups/{groupId}/profile/{profileId}")
    public RetailerProfile getRetailerProfileById(@PathVariable("retailerIntegrationId") String retailerIntegrationId,
                                                  @PathVariable("groupId") String groupId,
                                                  @PathVariable("profileId") String profileId) {
        Optional<RetailerProfile> optional = retailerIntegrationsService.
                getRetailerProfileById(retailerIntegrationId, groupId, profileId);
        if (optional.isPresent())
            return optional.get();
        throw new ResourceNotFoundException();
    }

    @PutMapping("/retailerIntegrations/{retailerIntegrationId}/groups/{groupId}/profile/{profileId}")
    public RetailerProfile updateRetailerProfile(@RequestBody RetailerProfile retailerProfile,
                                                 @PathVariable("retailerIntegrationId") String retailerIntegrationId,
                                                 @PathVariable("groupId") String groupId,
                                                 @PathVariable("profileId") String profileId) {
        Optional<RetailerProfile> optional = retailerIntegrationsService.
                updateRetailerProfile(retailerProfile, retailerIntegrationId, groupId, profileId);
        if (optional.isPresent())
            return optional.get();
        throw new ResourceNotFoundException();

    }

    @DeleteMapping("/retailerIntegrations/{retailerIntegrationId}/groups/{groupId}/profile/{profileId}")
    public void deleteRetailerProfile(@PathVariable("retailerIntegrationId") String retailerIntegrationId,
                                      @PathVariable("groupId") String groupId,
                                      @PathVariable("profileId") String profileId) {
        retailerIntegrationsService.deleteRetailerProfile(retailerIntegrationId, groupId, profileId);
    }
}
