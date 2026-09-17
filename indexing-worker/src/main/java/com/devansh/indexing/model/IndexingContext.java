package com.devansh.indexing.model;

import java.util.ArrayList;
import java.util.List;

import com.devansh.indexing.workspace.Workspace;

public class IndexingContext {

    private Workspace workspace;
    private List<SourceDocument> documents = new ArrayList<>();
    private List<CodeChunk> chunks = new ArrayList<>();

    public IndexingContext() {
    }

    private IndexingContext(Builder builder) {
        this.workspace = builder.workspace;
        this.documents = builder.documents;
        this.chunks = builder.chunks;
    }

    // --- Getters ---

    public Workspace getWorkspace() {
        return workspace;
    }

    public List<SourceDocument> getDocuments() {
        return documents;
    }

    public List<CodeChunk> getChunks() {
        return chunks;
    }

    // --- Setters ---

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public void setDocuments(List<SourceDocument> documents) {
        this.documents = documents;
    }

    public void setChunks(List<CodeChunk> chunks) {
        this.chunks = chunks;
    }

    // --- Builder ---

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Workspace workspace;
        
        private List<SourceDocument> documents = new ArrayList<>();
        private List<CodeChunk> chunks = new ArrayList<>();

        private Builder() {}

        public Builder workspace(Workspace workspace) {
            this.workspace = workspace;
            return this;
        }

        public Builder documents(List<SourceDocument> documents) {
            this.documents = documents;
            return this;
        }

        public Builder chunks(List<CodeChunk> chunks) {
            this.chunks = chunks;
            return this;
        }

        public IndexingContext build() {
            return new IndexingContext(this);
        }
    }
}