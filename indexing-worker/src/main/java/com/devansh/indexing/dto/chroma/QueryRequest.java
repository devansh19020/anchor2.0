package com.devansh.indexing.dto.chroma;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryRequest {

    @JsonProperty("query_embeddings")
    private List<List<Float>> queryEmbeddings;

    @JsonProperty("n_results")
    private int nResults;

    @JsonProperty("include")
    private List<String> include;

    @JsonProperty("where")
    private Map<String, Object> where;

    // Default constructor (required by Jackson for JSON deserialization)
    public QueryRequest() {
    }

    // Private constructor for Builder
    private QueryRequest(Builder builder) {
        this.queryEmbeddings = builder.queryEmbeddings;
        this.nResults = builder.nResults;
        this.include = builder.include;
        this.where = builder.where;
    }

    // --- Getters ---

    public List<List<Float>> getQueryEmbeddings() {
        return queryEmbeddings;
    }

    public int getNResults() {
        return nResults;
    }

    public List<String> getInclude() {
        return include;
    }

    public Map<String, Object> getWhere() {
        return where;
    }

    // --- Builder ---

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<List<Float>> queryEmbeddings;
        private int nResults;
        private List<String> include;
        private Map<String, Object> where;

        private Builder() {}

        public Builder queryEmbeddings(List<List<Float>> queryEmbeddings) {
            this.queryEmbeddings = queryEmbeddings;
            return this;
        }

        public Builder nResults(int nResults) {
            this.nResults = nResults;
            return this;
        }

        public Builder include(List<String> include) {
            this.include = include;
            return this;
        }

        public Builder where(Map<String, Object> where) {
            this.where = where;
            return this;
        }

        public QueryRequest build() {
            return new QueryRequest(this);
        }
    }

    // --- Static Factory Method (Remains unchanged) ---

    public static QueryRequest from(
            String workspaceId,
            float[] embedding,
            int topK,
            List<String> include
    ) {

        List<Float> embeddingList =
                new java.util.ArrayList<>();

        for (float value : embedding) {
            embeddingList.add(value);
        }

        return QueryRequest.builder()
                .queryEmbeddings(
                        List.of(embeddingList)
                )
                .nResults(topK)
                .include(include)
                .where(
                        Map.of(
                                "workspaceId",
                                workspaceId
                        )
                )
                .build();
    }
}