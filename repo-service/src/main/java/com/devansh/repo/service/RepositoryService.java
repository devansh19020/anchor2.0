package com.devansh.repo.service;

import com.devansh.repo.dto.RepositoryImportRequest;
import com.devansh.repo.dto.RepositoryResponse;
import com.devansh.repo.entity.Repository;
import com.devansh.repo.event.RepositoryImportedEvent;
import com.devansh.repo.kafka.RepositoryEventProducer;
import com.devansh.repo.repository.RepositoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RepositoryService {

    private final RepositoryRepository repositoryRepository;
    private final RepositoryEventProducer eventProducer;

    public RepositoryService(
            RepositoryRepository repositoryRepository,
            RepositoryEventProducer eventProducer) {
        this.repositoryRepository = repositoryRepository;
        this.eventProducer = eventProducer;
    }

    public RepositoryResponse importRepository(
            UUID userId,
            RepositoryImportRequest request) {

        if (repositoryRepository
                .existsByUserIdAndRepositoryUrl(
                        userId,
                        request.getRepositoryUrl())) {

            throw new IllegalArgumentException(
                    "Repository already imported"
            );
        }

        String[] parts = request.getRepositoryUrl()
                .replace("https://github.com/", "")
                .replace(".git", "")
                .split("/");

        if (parts.length < 2) {
            throw new IllegalArgumentException(
                    "Invalid GitHub repository URL"
            );
        }

        String owner = parts[0];
        String repositoryName = parts[1];

        Repository repository = new Repository();

        repository.setUserId(userId);
        repository.setRepositoryUrl(request.getRepositoryUrl());
        repository.setOwner(owner);
        repository.setRepositoryName(repositoryName);
        repository.setDefaultBranch("main");

        Repository saved = repositoryRepository.save(repository);

        String workspaceId = UUID.randomUUID().toString();

        RepositoryImportedEvent event =
                new RepositoryImportedEvent(
                        saved.getId(),
                        saved.getUserId(),
                        workspaceId,
                        saved.getRepositoryUrl(),
                        saved.getOwner(),
                        saved.getRepositoryName(),
                        saved.getDefaultBranch()
                );

        eventProducer.publishRepositoryImported(event);

        return toResponse(saved);
    }

    public List<RepositoryResponse> getUserRepositories(
            UUID userId) {

        return repositoryRepository
                .findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private RepositoryResponse toResponse(
            Repository repository) {

        return new RepositoryResponse(
                repository.getId(),
                repository.getUserId(),
                repository.getRepositoryUrl(),
                repository.getOwner(),
                repository.getRepositoryName(),
                repository.getDescription(),
                repository.getDefaultBranch(),
                repository.getLanguage(),
                repository.isPrivateRepository()
        );
    }
}