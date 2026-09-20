package com.devansh.indexing.embedding;

import com.devansh.indexing.model.CodeChunk;
import com.devansh.indexing.model.IndexingContext;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingContentBuilder contentBuilder;

    public EmbeddingService(
            EmbeddingModel embeddingModel,
            EmbeddingContentBuilder contentBuilder) {
        this.embeddingModel = embeddingModel;
        this.contentBuilder = contentBuilder;
    }

    public IndexingContext generateEmbeddings(IndexingContext context) {

        for (CodeChunk chunk : context.getChunks()) {
            chunk.setEmbeddingContent(contentBuilder.build(chunk));
            embeddingModel.embed(chunk);
        }

        return context;
    }
}