package com.devansh.indexing.service;

import java.nio.file.Path;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.devansh.indexing.exception.RepositoryPreparationException;
import com.devansh.indexing.github.GithubDownloader;
import com.devansh.indexing.model.Workspace;
import com.devansh.indexing.repository.RepositoryLayoutManager;
import com.devansh.indexing.util.ZipExtractor;
import com.devansh.indexing.workspace.WorkspaceCleaner;
import com.devansh.indexing.workspace.WorkspaceManager;

@Service
public class RepositoryPreparationService {

    private final WorkspaceManager workspaceManager;
    private final GithubDownloader githubDownloader;
    private final ZipExtractor zipExtractor;
    private final RepositoryLayoutManager repositoryLayoutManager;
    private final WorkspaceCleaner workspaceCleaner;

    public RepositoryPreparationService(
            WorkspaceManager workspaceManager,
            GithubDownloader githubDownloader,
            ZipExtractor zipExtractor,
            RepositoryLayoutManager repositoryLayoutManager,
            WorkspaceCleaner workspaceCleaner
    ) {
        this.workspaceManager = workspaceManager;
        this.githubDownloader = githubDownloader;
        this.zipExtractor = zipExtractor;
        this.repositoryLayoutManager = repositoryLayoutManager;
        this.workspaceCleaner = workspaceCleaner;
    }

    public Workspace prepareRepository(
            UUID workspaceId,
            String owner,
            String repository,
            String branch
    ) {

        Workspace workspace = workspaceManager.createWorkspace(workspaceId);

        try {

            // Step 1: Download repository ZIP
            Path zipFile = githubDownloader.downloadRepository(
                    workspace,
                    owner,
                    repository,
                    branch
            );

            // Step 2: Extract ZIP
            Path extractedDirectory = zipExtractor.extract(
                    workspace,
                    zipFile
            );

            // Step 3: Normalize GitHub folder structure
            repositoryLayoutManager.normalizeRepository(
                    workspace,
                    extractedDirectory
            );

            // Step 4: Cleanup temporary files
            workspaceCleaner.delete(zipFile);
            workspaceCleaner.delete(extractedDirectory);

            return workspace;

        } catch (Exception ex) {

            // Cleanup entire workspace if anything fails
            workspaceCleaner.delete(workspace.getRootPath());

            throw new RepositoryPreparationException(
                    "Failed to prepare repository workspace.",
                    ex
            );
        }
    }
}