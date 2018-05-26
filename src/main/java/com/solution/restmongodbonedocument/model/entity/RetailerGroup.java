package com.solution.restmongodbonedocument.model.entity;

import java.util.ArrayList;
import java.util.List;

public class RetailerGroup {
    private String id;
    private String name;
    private String description;
    private List<RetailerProfile> retailerProfiles;

    protected RetailerGroup() {
        this.retailerProfiles = new ArrayList<>();
    }

    public RetailerGroup(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    public RetailerGroup(String name, String description, List<RetailerProfile> retailerProfiles) {
        this.name = name;
        this.description = description;
        this.retailerProfiles = retailerProfiles;
    }

    public RetailerGroup(String id, String name, String description, List<RetailerProfile> retailerProfiles) {
        this(name, description, retailerProfiles);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RetailerProfile> getRetailerProfiles() {
        return retailerProfiles;
    }

    public void setRetailerProfiles(List<RetailerProfile> retailerProfiles) {
        this.retailerProfiles = retailerProfiles;
    }
}
