package com.devansh.indexing.dto.chroma;

public class CreateCollectionRequest {

    private String name;
    private boolean get_or_create;

    // Default constructor (highly recommended for JSON deserialization in Spring/Jackson)
    public CreateCollectionRequest() {
    }

    // All-args constructor (replaces @AllArgsConstructor)
    public CreateCollectionRequest(String name, boolean get_or_create) {
        this.name = name;
        this.get_or_create = get_or_create;
    }

    // --- Getters ---

    public String getName() {
        return name;
    }

    // Note: Lombok generates 'is' for primitive boolean getters
    public boolean isGet_or_create() {
        return get_or_create;
    }
}