package com.devansh.repo.event;

import java.util.UUID;

public class RepositoryImportedEvent {

    private UUID repositoryId;
    private UUID userId;
    private String workspaceId;
    private String repositoryUrl;
    private String owner;
    private String repositoryName;
    private String defaultBranch;

    public RepositoryImportedEvent() {
    }

    public RepositoryImportedEvent(
            UUID repositoryId,
            UUID userId,
            String workspaceId,
            String repositoryUrl,
            String owner,
            String repositoryName,
            String defaultBranch) {

        this.repositoryId = repositoryId;
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.repositoryUrl = repositoryUrl;
        this.owner = owner;
        this.repositoryName = repositoryName;
        this.defaultBranch = defaultBranch;
    }

    public UUID getRepositoryId() {
        return repositoryId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getWorkspaceId() {
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

    public String getDefaultBranch() {
        return defaultBranch;
    }
}