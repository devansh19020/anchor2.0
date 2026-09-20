package com.devansh.indexing.dto.chroma;

import java.util.List;
import java.util.Map;

public class AddEmbeddingsRequest {

    private List<String> ids;
    private List<String> documents;
    private List<float[]> embeddings;
    private List<Map<String, Object>> metadatas;

    // Default constructor
    public AddEmbeddingsRequest() {
    }

    // Private constructor for Builder
    private AddEmbeddingsRequest(Builder builder) {
        this.ids = builder.ids;
        this.documents = builder.documents;
        this.embeddings = builder.embeddings;
        this.metadatas = builder.metadatas;
    }

    // --- Getters ---

    public List<String> getIds() {
        return ids;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public List<float[]> getEmbeddings() {
        return embeddings;
    }

    public List<Map<String, Object>> getMetadatas() {
        return metadatas;
    }

    // --- Builder ---

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> ids;
        private List<String> documents;
        private List<float[]> embeddings;
        private List<Map<String, Object>> metadatas;

        private Builder() {}

        public Builder ids(List<String> ids) {
            this.ids = ids;
            return this;
        }

        public Builder documents(List<String> documents) {
            this.documents = documents;
            return this;
        }

        public Builder embeddings(List<float[]> embeddings) {
            this.embeddings = embeddings;
            return this;
        }

        public Builder metadatas(List<Map<String, Object>> metadatas) {
            this.metadatas = metadatas;
            return this;
        }

        public AddEmbeddingsRequest build() {
            return new AddEmbeddingsRequest(this);
        }
    }
}