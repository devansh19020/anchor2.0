package com.devansh.repo.dto;

import jakarta.validation.constraints.NotBlank;

public class RepositoryImportRequest {

    @NotBlank
    private String repositoryUrl;

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }
}