package com.devansh.repo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.devansh.repo.dto.GithubRepositoryResponse;
import com.devansh.repo.dto.RepositoryImportRequest;
import com.devansh.repo.dto.RepositoryResponse;
import com.devansh.repo.event.RepositoryImportedEvent;
import com.devansh.repo.github.GithubClient;
import com.devansh.repo.github.GithubUrlValidator;
import com.devansh.repo.github.GithubUrlValidator.GithubRepositoryInfo;
import com.devansh.repo.model.Repository;
import com.devansh.repo.producer.RepositoryEventProducer;
import com.devansh.repo.repository.RepositoryRepository;

@Service
public class RepositoryService {

    private final RepositoryRepository repositoryRepository;
    private final GithubUrlValidator githubUrlValidator;
    private final GithubClient githubClient;
    private final RepositoryEventProducer eventProducer;

    public RepositoryService(
            RepositoryRepository repositoryRepository,
            GithubUrlValidator githubUrlValidator,
            GithubClient githubClient,
            RepositoryEventProducer eventProducer
    ) {

        this.repositoryRepository = repositoryRepository;
        this.githubUrlValidator = githubUrlValidator;
        this.githubClient = githubClient;
        this.eventProducer = eventProducer;
    }

    public RepositoryResponse importRepository(
            UUID userId,
            RepositoryImportRequest request
    ) {

        /*
         * 1. Validate and parse GitHub URL
         */
        GithubRepositoryInfo githubInfo =
                githubUrlValidator.parse(
                        request.getRepositoryUrl()
                );

        /*
         * 2. Prevent duplicate imports
         */
        if (repositoryRepository
                .existsByUserIdAndRepositoryUrl(
                        userId,
                        request.getRepositoryUrl()
                )) {

            throw new IllegalArgumentException(
                    "Repository already imported"
            );
        }

        /*
         * 3. Fetch metadata from GitHub
         */
        GithubRepositoryResponse githubRepository =
                githubClient.fetchRepository(
                        githubInfo.owner(),
                        githubInfo.repositoryName()
                );

        /*
         * 4. Create isolated workspace ID
         */
        UUID workspaceId = UUID.randomUUID();

        /*
         * 5. Create repository record
         */
        Repository repository = new Repository();

        repository.setUserId(userId);

        repository.setWorkspaceId(
                workspaceId
        );

        repository.setRepositoryUrl(
                request.getRepositoryUrl()
        );

        repository.setOwner(
                githubInfo.owner()
        );

        repository.setRepositoryName(
                githubRepository.getName()
        );

        repository.setDescription(
                githubRepository.getDescription()
        );

        repository.setDefaultBranch(
                githubRepository.getDefaultBranch()
        );

        repository.setLanguage(
                githubRepository.getLanguage()
        );

        repository.setPrivateRepository(
                githubRepository.isPrivateRepository()
        );

        repository.setIndexingStatus(
                "IMPORTING"
        );

        /*
         * 6. Save to PostgreSQL
         */
        Repository saved =
                repositoryRepository.save(repository);

        /*
         * 7. Publish indexing event
         */
        RepositoryImportedEvent event =
                new RepositoryImportedEvent(

                        saved.getId(),

                        saved.getUserId(),

                        saved.getWorkspaceId()
                                .toString(),

                        saved.getRepositoryUrl(),

                        saved.getOwner(),

                        saved.getRepositoryName(),

                        saved.getDefaultBranch()
                );

        eventProducer.publishRepositoryImported(event);

        /*
         * 8. Return immediately.
         *
         * Indexing happens asynchronously.
         */
        return new RepositoryResponse(
                saved.getId(),
                saved.getUserId(),
                saved.getWorkspaceId(),
                saved.getRepositoryUrl(),
                saved.getOwner(),
                saved.getRepositoryName(),
                saved.getDescription(),
                saved.getDefaultBranch(),
                saved.getLanguage(),
                saved.isPrivateRepository(),
                saved.getIndexingStatus(),
                saved.getCreatedAt()
        );
    }

        public List<RepositoryResponse> getUserRepositories(UUID userId) {

        return repositoryRepository
                .findByUserId(userId)
                .stream()
                .map(repository -> new RepositoryResponse(
                        repository.getId(),
                        repository.getUserId(),
                        repository.getWorkspaceId(),
                        repository.getRepositoryUrl(),
                        repository.getOwner(),
                        repository.getRepositoryName(),
                        repository.getDescription(),
                        repository.getDefaultBranch(),
                        repository.getLanguage(),
                        repository.isPrivateRepository(),
                        repository.getIndexingStatus(),
                        repository.getCreatedAt()
                ))
                .toList();
        }
}