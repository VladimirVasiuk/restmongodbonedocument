package com.solution.restmongodbonedocument.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document (collection = "RetailerIntegrations")
public class RetailerIntegration {
    @Id
    private String id;
    private String name;
    private String description;
    List<RetailerGroup> retailerGroups;

    protected RetailerIntegration() {
        retailerGroups = new ArrayList<>();
    }

    public RetailerIntegration(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    public RetailerIntegration(String name, String description, List<RetailerGroup> retailerGroups) {
        this.name = name;
        this.description = description;
        this.retailerGroups = retailerGroups;
    }

    public RetailerIntegration(String id, String name, String description, List<RetailerGroup> retailerGroups) {
        this(name,description,retailerGroups);
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

    public List<RetailerGroup> getRetailerGroups() {
        return retailerGroups;
    }

    public void setRetailerGroups(List<RetailerGroup> retailerGroups) {
        this.retailerGroups = retailerGroups;
    }
}