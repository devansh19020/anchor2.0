package com.devansh.indexing.workspace;

import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;

public class Workspace {

    private UUID workspaceId;
    private Path rootPath;
    private Path sourcePath;
    private Instant createdAt;

    // Private constructor enforces object creation through the Builder
    private Workspace(Builder builder) {
        this.workspaceId = builder.workspaceId;
        this.rootPath = builder.rootPath;
        this.sourcePath = builder.sourcePath;
        this.createdAt = builder.createdAt;
    }

    public UUID getWorkspaceId() {
        return workspaceId;
    }

    public Path getRootPath() {
        return rootPath;
    }
    
    public Path getSourcePath() {
        return sourcePath;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    // Entry point for the builder
    public static Builder builder() {
        return new Builder();
    }

    // Static nested Builder class
    public static class Builder {
        private UUID workspaceId;
        private Path rootPath;
        private Path sourcePath;
        private Instant createdAt;

        // Private constructor prevents direct instantiation from outside
        private Builder() {}

        public Builder workspaceId(UUID workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder rootPath(Path rootPath) {
            this.rootPath = rootPath;
            return this;
        }

        public Builder sourcePath(Path sourcePath) {
            this.sourcePath = sourcePath;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Workspace build() {
            return new Workspace(this);
        }
    }
}