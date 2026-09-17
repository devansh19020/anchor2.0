package com.devansh.indexing.model;

import java.time.Instant;

public class VectorEmbedding {

    private final float[] vector;
    private final int dimensions;
    private final String model;
    private final Instant createdAt;

    private VectorEmbedding(Builder builder) {
        this.vector = builder.vector;
        this.dimensions = builder.dimensions;
        this.model = builder.model;
        this.createdAt = builder.createdAt;
    }

    // --- Getters ---

    public float[] getVector() {
        return vector;
    }

    public int getDimensions() {
        return dimensions;
    }

    public String getModel() {
        return model;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    // --- Builder ---

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private float[] vector;
        private int dimensions;
        private String model;
        private Instant createdAt;

        private Builder() {}

        public Builder vector(float[] vector) {
            this.vector = vector;
            return this;
        }

        public Builder dimensions(int dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public VectorEmbedding build() {
            return new VectorEmbedding(this);
        }
    }
}