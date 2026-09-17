package com.devansh.indexing.model;

public class SourceDocument {

    private SourceFile sourceFile;
    private String content;

    // Default constructor
    public SourceDocument() {
    }

    // Private constructor for Builder
    private SourceDocument(Builder builder) {
        this.sourceFile = builder.sourceFile;
        this.content = builder.content;
    }

    // --- Getters ---

    public SourceFile getSourceFile() {
        return sourceFile;
    }

    public String getContent() {
        return content;
    }

    // --- Builder ---

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private SourceFile sourceFile;
        private String content;

        private Builder() {}

        public Builder sourceFile(SourceFile sourceFile) {
            this.sourceFile = sourceFile;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public SourceDocument build() {
            return new SourceDocument(this);
        }
    }
}