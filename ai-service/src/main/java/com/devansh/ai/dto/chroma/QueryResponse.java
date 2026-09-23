package com.devansh.ai.dto.chroma;

import java.util.List;
import java.util.Map;

public class QueryResponse {

    private List<List<String>> ids;
    private List<List<String>> documents;
    private List<List<Map<String, Object>>> metadatas;
    private List<List<Double>> distances;

    // Default constructor (required by Jackson for JSON deserialization)
    public QueryResponse() {
    }

    // --- Getters ---

    public List<List<String>> getIds() {
        return ids;
    }

    public List<List<String>> getDocuments() {
        return documents;
    }

    public List<List<Map<String, Object>>> getMetadatas() {
        return metadatas;
    }

    public List<List<Double>> getDistances() {
        return distances;
    }

    // --- Setters ---

    public void setIds(List<List<String>> ids) {
        this.ids = ids;
    }

    public void setDocuments(List<List<String>> documents) {
        this.documents = documents;
    }

    public void setMetadatas(List<List<Map<String, Object>>> metadatas) {
        this.metadatas = metadatas;
    }

    public void setDistances(List<List<Double>> distances) {
        this.distances = distances;
    }
}