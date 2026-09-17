package com.devansh.indexing.model;

import java.nio.file.Path;
import java.util.UUID;

public class SourceFile {

    private UUID id;
    private UUID workspaceId;
    private String fileName;
    private Path relativePath;
    private Language language;
    private String packageName;
    private String className;

    // Default constructor
    public SourceFile() {
    }

    private SourceFile(Builder builder) {
        this.id = builder.id;
        this.workspaceId = builder.workspaceId;
        this.fileName = builder.fileName;
        this.relativePath = builder.relativePath;
        this.language = builder.language;
        this.packageName = builder.packageName;
        this.className = builder.className;
    }

    // --- Getters ---

    public UUID getId() {
        return id;
    }

    public UUID getWorkspaceId() {
        return workspaceId;
    }

    public String getFileName() {
        return fileName;
    }

    public Path getRelativePath() {
        return relativePath;
    }

    public Language getLanguage() {
        return language;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    // --- Builder ---

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private UUID workspaceId;
        private String fileName;
        private Path relativePath;
        private Language language;
        private String packageName;
        private String className;

        private Builder() {}

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder workspaceId(UUID workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder relativePath(Path relativePath) {
            this.relativePath = relativePath;
            return this;
        }

        public Builder language(Language language) {
            this.language = language;
            return this;
        }

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder className(String className) {
            this.className = className;
            return this;
        }

        public SourceFile build() {
            return new SourceFile(this);
        }
    }
}