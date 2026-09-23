package com.devansh.indexing.model;

import java.time.Instant;
import java.util.UUID;

public record IndexingStatusUpdate(
        UUID repositoryId,
        IndexingStatus status,
        String message,
        Instant updatedAt
) {}