package com.devansh.anchor.indexing;

import org.springframework.stereotype.Service;

import com.devansh.anchor.embedding.EmbeddingService;
import com.devansh.anchor.model.IndexingContext;
import com.devansh.anchor.model.Workspace;
import com.devansh.anchor.vectorstore.VectorStore;

@Service
public class RepositoryIndexingService {

    private final RepositoryParserService parserService;

    private final ChunkingService chunkingService;

    private final EmbeddingService embeddingService;

    private final VectorStore vectorStore;

    public RepositoryIndexingService(
            RepositoryParserService parserService,
            ChunkingService chunkingService,
            EmbeddingService embeddingService,
            VectorStore vectorStore
    ) {
        this.parserService = parserService;
        this.chunkingService = chunkingService;
        this.embeddingService = embeddingService;
        this.vectorStore = vectorStore;
    }

    public IndexingContext indexRepository(
            Workspace workspace
    ) {

        IndexingContext context =
                parserService.parseRepository(workspace);

        context =
                chunkingService.chunkDocuments(context);

        context =
                embeddingService.generateEmbeddings(context);

        vectorStore.store(context.getChunks());

        return context;

    }

}