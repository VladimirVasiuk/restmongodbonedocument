package com.solution.restmongodbonedocument.service;

import com.solution.restmongodbonedocument.model.entity.RetailerGroup;
import com.solution.restmongodbonedocument.model.entity.RetailerIntegration;
import com.solution.restmongodbonedocument.model.entity.RetailerProfile;
import com.solution.restmongodbonedocument.model.entity.Sequence;
import com.solution.restmongodbonedocument.model.repository.RetailerIntegrationRepository;
import com.solution.restmongodbonedocument.model.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RetailerIntegrationsService {

    private RetailerIntegrationRepository retailerIntegrationRepository;
    private SequenceRepository sequenceRepository;

    @Autowired
    public RetailerIntegrationsService(RetailerIntegrationRepository retailerIntegrationRepository,
                                       SequenceRepository sequenceRepository) {
        this.retailerIntegrationRepository = retailerIntegrationRepository;
        this.sequenceRepository = sequenceRepository;
    }

    public List<RetailerIntegration> getAllRetailerIntegration() {
        return retailerIntegrationRepository.findAll();
    }

    public Optional<RetailerIntegration> getRetailerIntegrationById(String id) {
        return retailerIntegrationRepository.findById(id);
    }

    public RetailerIntegration insertRetailerIntegration(RetailerIntegration retailerIntegration) {
        return retailerIntegrationRepository.insert(retailerIntegration);
    }

    public RetailerIntegration updateRetailerIntegration(RetailerIntegration retailerIntegration,
                                                         String retailerIntegrationId) {
        return retailerIntegrationRepository.save(retailerIntegration);
    }

    public void deleteRetailerIntegrationById(String id) {
        retailerIntegrationRepository.deleteById(id);
    }

    public List<RetailerGroup> getAllRetailerGroupByRetailerIntegrationId(String retailerIntegrationId) {
        Optional<RetailerIntegration> optional = getRetailerIntegrationById(retailerIntegrationId);
        if (!optional.isPresent())
            return new ArrayList<>();
        return optional.get().getRetailerGroups();
    }

    public Optional<RetailerGroup> insertRetailerGroup(RetailerGroup retailerGroup,
                                                       String retailerIntegrationId) {
        Optional<RetailerIntegration> optional = getRetailerIntegrationById(retailerIntegrationId);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        RetailerIntegration retailerIntegrationById = optional.get();
        retailerGroup.setId(sequenceRepository.save(new Sequence()).getId());
        retailerIntegrationById.getRetailerGroups().add(retailerGroup);
        retailerIntegrationRepository.save(retailerIntegrationById);
        return Optional.of(retailerGroup);
    }


    private Optional<RetailerGroup> findRetailerGroup(String retailerGroupId, RetailerIntegration retailerIntegration) {
        for (RetailerGroup retailerGroup : retailerIntegration.getRetailerGroups()) {
            if (retailerGroupId.equals(retailerGroup.getId()))
                return Optional.of(retailerGroup);
        }
        return Optional.empty();
    }

    public Optional<RetailerGroup> getRetailerGroupById(String retailerIntegrationId, String retailerGroupId) {
        Optional<RetailerIntegration> retailerIntegrationOptional = getRetailerIntegrationById(retailerIntegrationId);
        if (retailerIntegrationOptional.isPresent()) {
            return findRetailerGroup(retailerGroupId, retailerIntegrationOptional.get());
        }
        return Optional.empty();
    }

    public List<RetailerProfile> getAllRetailerProfile(String retailerIntegrationId, String retailerGroupId) {
        Optional<RetailerIntegration> retailerIntegrationOptional = getRetailerIntegrationById(retailerIntegrationId);
        if (!retailerIntegrationOptional.isPresent()) {
            return new ArrayList<>();
        }

        Optional<RetailerGroup> retailerGroupOptional =
                findRetailerGroup(retailerGroupId, retailerIntegrationOptional.get());
        if (!retailerGroupOptional.isPresent()) {
            return new ArrayList<>();
        }

        return retailerGroupOptional.get().getRetailerProfiles();
    }

    public Optional<RetailerProfile> insertRetailerProfile(RetailerProfile retailerProfile,
                                                           String retailerIntegrationId,
                                                           String retailerGroupId) {
        Optional<RetailerIntegration> retailerIntegrationOptional = getRetailerIntegrationById(retailerIntegrationId);
        if (!retailerIntegrationOptional.isPresent()) {
            return Optional.empty();
        }

        Optional<RetailerGroup> retailerGroupOptional =
                findRetailerGroup(retailerGroupId, retailerIntegrationOptional.get());
        if (!retailerGroupOptional.isPresent()) {
            return Optional.empty();
        }
        retailerProfile.setId(sequenceRepository.save(new Sequence()).getId());
        retailerGroupOptional.get().getRetailerProfiles().add(retailerProfile);
        retailerIntegrationRepository.save(retailerIntegrationOptional.get());

        return Optional.of(retailerProfile);
    }

    public Optional<RetailerGroup> updateRetailerGroup(RetailerGroup group,
                                                       String retailerIntegrationId,
                                                       String retailerGroupId) {
        Optional<RetailerIntegration> retailerIntegrationOptional = getRetailerIntegrationById(retailerIntegrationId);
        if (!retailerIntegrationOptional.isPresent()) {
            return Optional.empty();
        }

        Optional<RetailerGroup> retailerGroupOptional =
                findRetailerGroup(retailerGroupId, retailerIntegrationOptional.get());
        if (!retailerGroupOptional.isPresent()) {
            return Optional.empty();
        }
        RetailerGroup retailerGroup = retailerGroupOptional.get();
        retailerGroup.setId(group.getId());
        retailerGroup.setName(group.getName());
        retailerGroup.setDescription(group.getDescription());
        retailerGroup.setRetailerProfiles(group.getRetailerProfiles());
        retailerIntegrationRepository.save(retailerIntegrationOptional.get());
        return Optional.of(group);
    }

    public void deleteRetailerGroup(String retailerIntegrationId, String groupId) {
        Optional<RetailerIntegration> retailerIntegrationOptional = getRetailerIntegrationById(retailerIntegrationId);
        if (retailerIntegrationOptional.isPresent()) {
            List<RetailerGroup> retailerGroups = retailerIntegrationOptional.get().getRetailerGroups();
            retailerGroups.removeIf(retailerGroup -> retailerGroup.getId().equals(groupId));
            retailerIntegrationRepository.save(retailerIntegrationOptional.get());
        }
    }

    private Optional<RetailerProfile> findRetailerProfile(String retailePprofileId,
                                                          RetailerGroup retailerGroup) {
        for (RetailerProfile retailerProfile : retailerGroup.getRetailerProfiles()) {
            if (retailePprofileId.equals(retailerProfile.getId()))
                return Optional.of(retailerProfile);
        }
        return Optional.empty();
    }

    public Optional<RetailerProfile> getRetailerProfileById(String retailerIntegrationId,
                                                            String retailerGroupId, String profileId) {
        Optional<RetailerIntegration> retailerIntegrationOptional = getRetailerIntegrationById(retailerIntegrationId);
        Optional<RetailerGroup> retailerGroupOptional = Optional.empty();
        if (retailerIntegrationOptional.isPresent()) {
            retailerGroupOptional = findRetailerGroup(retailerGroupId, retailerIntegrationOptional.get());
        }
        if (retailerGroupOptional.isPresent()) {
            return findRetailerProfile(profileId, retailerGroupOptional.get());
        }
        return Optional.empty();
    }

    public Optional<RetailerProfile> updateRetailerProfile(RetailerProfile retailerProfile,
                                                           String retailerIntegrationId,
                                                           String retailerGroupId, String profileId) {
        Optional<RetailerIntegration> retailerIntegrationOptional = getRetailerIntegrationById(retailerIntegrationId);
        if (!retailerIntegrationOptional.isPresent()) {
            return Optional.empty();
        }

        Optional<RetailerGroup> retailerGroupOptional =
                findRetailerGroup(retailerGroupId, retailerIntegrationOptional.get());
        if (!retailerGroupOptional.isPresent()) {
            return Optional.empty();
        }

        Optional<RetailerProfile> retailerProfileOptional =
                findRetailerProfile(profileId, retailerGroupOptional.get());
        if (!retailerProfileOptional.isPresent()) {
            return Optional.empty();
        }

        RetailerProfile profile = retailerProfileOptional.get();
        profile.setId(retailerProfile.getId());
        profile.setName(retailerProfile.getName());
        profile.setDescription(retailerProfile.getDescription());
        retailerIntegrationRepository.save(retailerIntegrationOptional.get());
        return Optional.of(retailerProfile);
    }

    public void deleteRetailerProfile(String retailerIntegrationId, String groupId, String profileId) {
        Optional<RetailerIntegration> retailerIntegrationOptional = getRetailerIntegrationById(retailerIntegrationId);
        if (retailerIntegrationOptional.isPresent()) {
            Optional<RetailerGroup> retailerGroupOptional = findRetailerGroup(groupId, retailerIntegrationOptional.get());
            if (retailerGroupOptional.isPresent()) {
                List<RetailerProfile> retailerProfiles = retailerGroupOptional.get().getRetailerProfiles();
                retailerProfiles.removeIf(retailerProfile -> retailerProfile.getId().equals(profileId));
            }
            retailerIntegrationRepository.save(retailerIntegrationOptional.get());
        }
    }

    public void deleteAll() {
        retailerIntegrationRepository.deleteAll();
    }

}
