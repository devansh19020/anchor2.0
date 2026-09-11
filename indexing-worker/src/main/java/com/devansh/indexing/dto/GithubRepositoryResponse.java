package com.devansh.indexing.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepositoryResponse {

    private String name;

    @JsonProperty("full_name")
    private String fullName;

    private String description;

    @JsonProperty("default_branch")
    private String defaultBranch;

    private String language;

    @JsonProperty("private")
    private boolean privateRepository;

}