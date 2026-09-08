package com.devansh.repo.controller;

import com.devansh.repo.dto.RepositoryImportRequest;
import com.devansh.repo.dto.RepositoryResponse;
import com.devansh.repo.service.RepositoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/repositories")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(
            RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @PostMapping("/import")
    public ResponseEntity<RepositoryResponse> importRepository(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody RepositoryImportRequest request) {

        RepositoryResponse response =
                repositoryService.importRepository(
                        UUID.fromString(userId),
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<RepositoryResponse>> getRepositories(
            @RequestHeader("X-User-Id") String userId) {

        return ResponseEntity.ok(
                repositoryService.getUserRepositories(
                        UUID.fromString(userId)
                )
        );
    }
}