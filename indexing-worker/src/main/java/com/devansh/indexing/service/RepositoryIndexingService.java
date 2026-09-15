package com.devansh.indexing.service;

import java.nio.file.Path;
import java.util.UUID;

import org.springframework.data.redis.core.index.IndexDefinition.IndexingContext;
import org.springframework.stereotype.Service;

import com.devansh.indexing.event.RepositoryImportedEvent;
import com.devansh.indexing.github.GithubDownloader;
import com.devansh.indexing.util.ZipExtractor;
import com.devansh.indexing.workspace.RepositoryLayoutManager;
import com.devansh.indexing.workspace.Workspace;
import com.devansh.indexing.workspace.WorkspaceCleaner;
import com.devansh.indexing.workspace.WorkspaceManager;

@Service
public class RepositoryIndexingService {

    private final WorkspaceManager workspaceManager;
    private final GithubDownloader githubDownloader;
    private final ZipExtractor zipExtractor;
    private final RepositoryLayoutManager repositoryLayoutManager;
    private final WorkspaceCleaner workspaceCleaner;

    public RepositoryIndexingService(
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

    public void indexRepository(
            RepositoryImportedEvent event
    ) {

        UUID workspaceId =
                UUID.fromString(
                        event.getWorkspaceId()
                );

        Workspace workspace =
                workspaceManager.createWorkspace(
                        workspaceId
                );

        githubDownloader.downloadRepository(
                workspace,
                event.getOwner(),
                event.getRepositoryName(),
                event.getDefaultBranch()
        );

        System.out.println(
                "Repository downloaded successfully: "
                        + event.getRepositoryUrl()
        );

        System.out.println(
                "ZIP path: "
                        + workspace
                        .getRootPath()
                        .resolve("repository.zip")
        );

        Path zipPath = workspace.getRootPath().resolve("repository.zip");
        Path extractedDirectory = zipExtractor.extract(workspace, zipPath);

        System.out.println("Repository extracted successfully.");

        repositoryLayoutManager.normalizeRepository(
                    workspace,
                    extractedDirectory
            );

        workspaceCleaner.delete(zipPath);
        workspaceCleaner.delete(extractedDirectory);

        System.out.println("Repository prepared successfully.");

        IndexingContext context = repositoryParserService.parseRepository(workspace);

        System.out.println(
                "Repository parsed successfully. Documents: "
                + context.getDocuments().size());
        }
}