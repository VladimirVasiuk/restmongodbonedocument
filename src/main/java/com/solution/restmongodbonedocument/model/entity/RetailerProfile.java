package com.solution.restmongodbonedocument.model.entity;


public class RetailerProfile {
    private String id;
    private String name;
    private String description;

    protected RetailerProfile() {

    }

    public RetailerProfile(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public RetailerProfile(String id, String name, String description) {
        this(name,description);
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
}
