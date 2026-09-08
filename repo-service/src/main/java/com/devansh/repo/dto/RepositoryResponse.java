package com.devansh.repo.dto;

import java.util.UUID;

public class RepositoryResponse {

    private UUID repositoryId;
    private UUID userId;
    private String repositoryUrl;
    private String owner;
    private String repositoryName;
    private String description;
    private String defaultBranch;
    private String language;
    private boolean privateRepository;

    public RepositoryResponse(
            UUID repositoryId,
            UUID userId,
            String repositoryUrl,
            String owner,
            String repositoryName,
            String description,
            String defaultBranch,
            String language,
            boolean privateRepository) {

        this.repositoryId = repositoryId;
        this.userId = userId;
        this.repositoryUrl = repositoryUrl;
        this.owner = owner;
        this.repositoryName = repositoryName;
        this.description = description;
        this.defaultBranch = defaultBranch;
        this.language = language;
        this.privateRepository = privateRepository;
    }

    public UUID getRepositoryId() {
        return repositoryId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public String getOwner() {
        return owner;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getDescription() {
        return description;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isPrivateRepository() {
        return privateRepository;
    }
}