package com.devansh.indexing.service;

import org.springframework.stereotype.Service;

import com.devansh.indexing.dto.GithubRepositoryResponse;
import com.devansh.indexing.dto.RepositoryImportResponse;
import com.devansh.indexing.exception.InvalidGithubUrlException;
import com.devansh.indexing.github.GithubClient;
import com.devansh.indexing.github.GithubUrlValidator;
import com.devansh.indexing.indexing.RepositoryIndexingService;
import com.devansh.indexing.model.IndexingContext;
import com.devansh.indexing.model.Workspace;

@Service
public class RepositoryImportService {

    private final GithubUrlValidator githubUrlValidator;

    private final GithubClient githubClient;

    private final RepositoryPreparationService repositoryPreparationService;

    private final RepositoryIndexingService repositoryIndexingService;

    public RepositoryImportService(
            GithubUrlValidator githubUrlValidator,
            GithubClient githubClient,
            RepositoryPreparationService repositoryPreparationService,
            RepositoryIndexingService repositoryIndexingService
    ) {
        this.githubUrlValidator = githubUrlValidator;
        this.githubClient = githubClient;
        this.repositoryPreparationService = repositoryPreparationService;
        this.repositoryIndexingService = repositoryIndexingService;
    }

    public RepositoryImportResponse importRepository(String repositoryUrl) {

        // Step 1: Validate GitHub URL
        if (!githubUrlValidator.isValidGithubUrl(repositoryUrl)) {
            throw new InvalidGithubUrlException(
                    "Invalid GitHub repository URL."
            );
        }

        // Step 2: Extract owner and repository name
        String owner =
                githubUrlValidator.extractOwner(repositoryUrl);

        String repository =
                githubUrlValidator.extractRepository(repositoryUrl);

        // Step 3: Fetch repository metadata
        GithubRepositoryResponse githubRepository =
                githubClient.fetchRepository(
                        owner,
                        repository
                );

        // Step 4: Download, extract and prepare workspace
        Workspace workspace =
                repositoryPreparationService.prepareRepository(
                        owner,
                        repository,
                        githubRepository.getDefaultBranch()
                );

        // Step 5: Parse, chunk, embed and store
        IndexingContext indexingContext =
                repositoryIndexingService.indexRepository(
                        workspace
                );

        // Step 6: Return import and indexing details
        return RepositoryImportResponse.builder()
                .owner(owner)
                .repositoryName(githubRepository.getName())
                .description(githubRepository.getDescription())
                .defaultBranch(githubRepository.getDefaultBranch())
                .language(githubRepository.getLanguage())
                .privateRepository(githubRepository.isPrivateRepository())
                .workspaceId(workspace.getWorkspaceId().toString())
                .localPath(workspace.getSourcePath().toString())
                .filesIndexed(
                        indexingContext.getDocuments().size()
                )
                .chunksIndexed(
                        indexingContext.getChunks().size()
                )
                .build();
    }

}