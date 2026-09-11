package com.devansh.indexing.model;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VectorEmbedding {

    private final float[] vector;

    private final int dimensions;

    private final String model;

    private final Instant createdAt;

}