package com.devansh.indexing.service;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.devansh.indexing.chunking.ChunkingService;
import com.devansh.indexing.embedding.EmbeddingService;
import com.devansh.indexing.event.RepositoryImportedEvent;
import com.devansh.indexing.github.GithubDownloader;
import com.devansh.indexing.model.IndexingContext;
import com.devansh.indexing.model.SourceDocument;
import com.devansh.indexing.parser.RepositoryParserService;
import com.devansh.indexing.util.ZipExtractor;
import com.devansh.indexing.vectorstore.VectorStore;
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
    private final RepositoryParserService repositoryParserService;
    private final ChunkingService chunkingService;
    private final EmbeddingService embeddingService;
    private final VectorStore vectorStore;

    public RepositoryIndexingService(
            WorkspaceManager workspaceManager,
            GithubDownloader githubDownloader,
            ZipExtractor zipExtractor,
            RepositoryLayoutManager repositoryLayoutManager,
            WorkspaceCleaner workspaceCleaner,
            RepositoryParserService repositoryParserService,
            ChunkingService chunkingService,
            EmbeddingService embeddingService,
            VectorStore vectorStore
    ) {
        this.workspaceManager = workspaceManager;
        this.githubDownloader = githubDownloader;
        this.zipExtractor = zipExtractor;
        this.repositoryLayoutManager = repositoryLayoutManager;
        this.workspaceCleaner = workspaceCleaner;
        this.repositoryParserService = repositoryParserService;
        this.chunkingService = chunkingService;
        this.embeddingService = embeddingService;
        this.vectorStore = vectorStore;
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

        List<SourceDocument> documents =
        repositoryParserService.parseRepository(workspace);

        System.out.println(
                "Repository parsed successfully. Documents: "
                        + documents.size()
        );

        IndexingContext context = IndexingContext.builder()
                .workspace(workspace)
                .documents(documents)
                .build();

        context = chunkingService.chunkDocuments(context);

        System.out.println(
                "Chunks created: " + context.getChunks().size()
        );

        context = embeddingService.generateEmbeddings(context);

        System.out.println("Embedding generation completed.");

        vectorStore.store(context.getChunks());

        System.out.println("Chunks stored in ChromaDB.");

        System.out.println(
                "Total vectors in collection: " + vectorStore.count()
        );
    }

}