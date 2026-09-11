package com.devansh.indexing.event;

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

    public void setRepositoryId(UUID repositoryId) {
        this.repositoryId = repositoryId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }
}