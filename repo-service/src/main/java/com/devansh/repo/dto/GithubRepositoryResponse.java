package com.devansh.repo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubRepositoryResponse {

    private String name;

    private String fullName;

    private String description;

    private String language;

    @JsonProperty("default_branch")
    private String defaultBranch;

    @JsonProperty("private")
    private boolean privateRepository;

    public GithubRepositoryResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public boolean isPrivateRepository() {
        return privateRepository;
    }

    public void setPrivateRepository(boolean privateRepository) {
        this.privateRepository = privateRepository;
    }
}