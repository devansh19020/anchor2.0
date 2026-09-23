package com.devansh.indexing.service;

import com.devansh.indexing.model.IndexingStatus;
import com.devansh.indexing.model.IndexingStatusUpdate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class IndexingStatusService {

    private static final String KEY_PREFIX = "anchor:indexing:";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public IndexingStatusService(
            StringRedisTemplate redisTemplate,
            ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void updateStatus(
            UUID repositoryId,
            IndexingStatus status,
            String message) {

        IndexingStatusUpdate update = new IndexingStatusUpdate(
                repositoryId,
                status,
                message,
                Instant.now()
        );

        try {
            String json = objectMapper.writeValueAsString(update);

            redisTemplate.opsForValue().set(
                    KEY_PREFIX + repositoryId,
                    json,
                    7,
                    TimeUnit.DAYS
            );

        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "Failed to serialize indexing status", e);
        }
    }

    public IndexingStatusUpdate getStatus(UUID repositoryId) {
        String json = redisTemplate.opsForValue().get(
                KEY_PREFIX + repositoryId
        );

        if (json == null) {
            return null;
        }

        try {
            return objectMapper.readValue(
                    json, IndexingStatusUpdate.class
            );
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                    "Failed to deserialize indexing status", e);
        }
    }
}