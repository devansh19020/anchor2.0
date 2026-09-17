package com.devansh.indexing.model;

import java.util.UUID;

public class CodeChunk {

    private UUID id;
    private UUID workspaceId;
    private SourceFile sourceFile;
    private String content;
    private int chunkIndex;
    private int startOffset;
    private int endOffset;
    private VectorEmbedding embedding;
    private String embeddingContent;

    // Default constructor
    public CodeChunk() {
    }

    // Private constructor for Builder
    private CodeChunk(Builder builder) {
        this.id = builder.id;
        this.workspaceId = builder.workspaceId;
        this.sourceFile = builder.sourceFile;
        this.content = builder.content;
        this.chunkIndex = builder.chunkIndex;
        this.startOffset = builder.startOffset;
        this.endOffset = builder.endOffset;
        this.embedding = builder.embedding;
        this.embeddingContent = builder.embeddingContent;
    }

    // --- Getters ---

    public UUID getId() {
        return id;
    }

    public UUID getWorkspaceId() {
        return workspaceId;
    }

    public SourceFile getSourceFile() {
        return sourceFile;
    }

    public String getContent() {
        return content;
    }

    public int getChunkIndex() {
        return chunkIndex;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public VectorEmbedding getEmbedding() {
        return embedding;
    }

    public String getEmbeddingContent() {
        return embeddingContent;
    }

    // --- Setters ---

    public void setId(UUID id) {
        this.id = id;
    }

    public void setWorkspaceId(UUID workspaceId) {
        this.workspaceId = workspaceId;
    }

    public void setSourceFile(SourceFile sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChunkIndex(int chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }

    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }

    public void setEmbedding(VectorEmbedding embedding) {
        this.embedding = embedding;
    }

    public void setEmbeddingContent(String embeddingContent) {
        this.embeddingContent = embeddingContent;
    }

    // --- Builder ---

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private UUID workspaceId;
        private SourceFile sourceFile;
        private String content;
        private int chunkIndex;
        private int startOffset;
        private int endOffset;
        private VectorEmbedding embedding;
        private String embeddingContent;

        private Builder() {}

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder workspaceId(UUID workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder sourceFile(SourceFile sourceFile) {
            this.sourceFile = sourceFile;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder chunkIndex(int chunkIndex) {
            this.chunkIndex = chunkIndex;
            return this;
        }

        public Builder startOffset(int startOffset) {
            this.startOffset = startOffset;
            return this;
        }

        public Builder endOffset(int endOffset) {
            this.endOffset = endOffset;
            return this;
        }

        public Builder embedding(VectorEmbedding embedding) {
            this.embedding = embedding;
            return this;
        }

        public Builder embeddingContent(String embeddingContent) {
            this.embeddingContent = embeddingContent;
            return this;
        }

        public CodeChunk build() {
            return new CodeChunk(this);
        }
    }
}