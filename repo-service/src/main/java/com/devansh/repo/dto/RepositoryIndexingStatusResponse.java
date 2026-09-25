package com.devansh.repo.dto;

import java.time.Instant;
import java.util.UUID;

public record RepositoryIndexingStatusResponse(
        UUID repositoryId,
        String status,
        String message,
        Instant updatedAt
) {}