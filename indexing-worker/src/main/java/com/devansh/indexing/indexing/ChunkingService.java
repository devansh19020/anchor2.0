package com.devansh.indexing.indexing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devansh.indexing.chunker.ChunkingStrategy;
import com.devansh.indexing.model.CodeChunk;
import com.devansh.indexing.model.IndexingContext;
import com.devansh.indexing.model.SourceDocument;

@Service
public class ChunkingService {

    private final ChunkingStrategy chunkingStrategy;

    public ChunkingService(
            ChunkingStrategy chunkingStrategy
    ) {
        this.chunkingStrategy = chunkingStrategy;
    }

    public IndexingContext chunkDocuments(
            IndexingContext context
    ) {

        List<CodeChunk> chunks = new ArrayList<>();

        for (SourceDocument document : context.getDocuments()) {

            chunks.addAll(
                    chunkingStrategy.chunk(document)
            );

        }

        context.setChunks(chunks);

        return context;

    }

}