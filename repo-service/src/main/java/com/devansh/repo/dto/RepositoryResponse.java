package com.devansh.repo.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class RepositoryResponse {

    private UUID id;
    private UUID userId;
    private UUID workspaceId;

    private String repositoryUrl;
    private String owner;
    private String repositoryName;
    private String description;
    private String defaultBranch;
    private String language;

    private boolean privateRepository;

    private String indexingStatus;

    private LocalDateTime createdAt;

    public RepositoryResponse() {
    }

    public RepositoryResponse(
            UUID id,
            UUID userId,
            UUID workspaceId,
            String repositoryUrl,
            String owner,
            String repositoryName,
            String description,
            String defaultBranch,
            String language,
            boolean privateRepository,
            String indexingStatus,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.repositoryUrl = repositoryUrl;
        this.owner = owner;
        this.repositoryName = repositoryName;
        this.description = description;
        this.defaultBranch = defaultBranch;
        this.language = language;
        this.privateRepository = privateRepository;
        this.indexingStatus = indexingStatus;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getWorkspaceId() {
        return workspaceId;
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

    public String getIndexingStatus() {
        return indexingStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}