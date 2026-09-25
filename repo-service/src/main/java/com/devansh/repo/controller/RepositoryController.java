package com.devansh.repo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devansh.repo.dto.RepositoryImportRequest;
import com.devansh.repo.dto.RepositoryIndexingStatusResponse;
import com.devansh.repo.dto.RepositoryResponse;
import com.devansh.repo.service.RepositoryService;
import com.devansh.repo.service.RepositoryStatusService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/repositories")
public class RepositoryController {

    private final RepositoryService repositoryService;
    private final RepositoryStatusService repositoryStatusService;

    public RepositoryController(
            RepositoryService repositoryService,
            RepositoryStatusService repositoryStatusService) {
        this.repositoryService = repositoryService;
        this.repositoryStatusService = repositoryStatusService;
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
    
    @GetMapping("/{repositoryId}/status")
    public ResponseEntity<RepositoryIndexingStatusResponse> getStatus(
                @PathVariable UUID repositoryId,
                @RequestHeader("X-User-Id") String userId) {

        RepositoryIndexingStatusResponse response =
                repositoryStatusService.getStatus(
                        repositoryId,
                        UUID.fromString(userId)
                );

        return ResponseEntity.ok(response);
        }

        @GetMapping("/workspaces/{workspaceId}/access")
        public ResponseEntity<Boolean> checkWorkspaceAccess(
                @PathVariable UUID workspaceId,
                @RequestHeader("X-User-Id") UUID userId) {

        boolean ownsWorkspace =
                repositoryService.existsByWorkspaceIdAndUserId(
                        workspaceId, userId);

        return ResponseEntity.ok(ownsWorkspace);
        }
}