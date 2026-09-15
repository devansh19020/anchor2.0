package com.devansh.indexing.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "workspace")
public class WorkspaceProperties {

    private String rootDirectory;

    public String getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

}