package com.devansh.indexing.dto.chroma;

public class CreateCollectionResponse {

    private String id;
    private String name;

    // Default constructor (required for JSON deserialization in Spring/Jackson)
    public CreateCollectionResponse() {
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // --- Setters ---

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}