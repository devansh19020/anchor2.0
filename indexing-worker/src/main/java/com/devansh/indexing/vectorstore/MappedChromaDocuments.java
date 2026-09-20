package com.devansh.indexing.vectorstore;

import java.util.List;
import java.util.Map;

public class MappedChromaDocuments {

    private final List<String> ids;
    private final List<String> documents;
    private final List<float[]> embeddings;
    private final List<Map<String, Object>> metadatas;

    private MappedChromaDocuments(Builder builder) {
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

        public MappedChromaDocuments build() {
            return new MappedChromaDocuments(this);
        }
    }
}