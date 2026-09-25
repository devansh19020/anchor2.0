package com.devansh.repo.service;

import java.util.UUID;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.devansh.repo.dto.RepositoryIndexingStatusResponse;
import com.devansh.repo.exception.RepositoryAccessDeniedException;
import com.devansh.repo.model.Repository;
import com.devansh.repo.repository.RepositoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RepositoryStatusService {

    private static final String KEY_PREFIX = "anchor:indexing:";

    private final RepositoryRepository repositoryRepository;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public RepositoryStatusService(
            RepositoryRepository repositoryRepository,
            StringRedisTemplate redisTemplate,
            ObjectMapper objectMapper) {
        this.repositoryRepository = repositoryRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public RepositoryIndexingStatusResponse getStatus(
            UUID repositoryId,
            UUID userId) {

        Repository repository = repositoryRepository
                .findById(repositoryId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Repository not found"));

        if (!repository.getUserId().equals(userId)) {
            throw new RepositoryAccessDeniedException(
                        "You do not have access to this repository"
                );
        }

        String json = redisTemplate.opsForValue()
                .get(KEY_PREFIX + repositoryId);

        // The worker may not have processed the Kafka event yet.
        if (json == null) {
            return new RepositoryIndexingStatusResponse(
                    repositoryId,
                    repository.getIndexingStatus(),
                    "Waiting for indexing worker",
                    null
            );
        }

        try {
            return objectMapper.readValue(
                    json,
                    RepositoryIndexingStatusResponse.class
            );
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "Failed to read repository indexing status", e);
        }
    }
}