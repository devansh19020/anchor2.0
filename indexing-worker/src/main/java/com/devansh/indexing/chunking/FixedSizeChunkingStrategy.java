package com.devansh.indexing.chunking;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.devansh.indexing.model.CodeChunk;
import com.devansh.indexing.model.SourceDocument;
import com.devansh.indexing.properties.ChunkingProperties;

@Component
public class FixedSizeChunkingStrategy implements ChunkingStrategy {

        private final ChunkingProperties properties;

        public FixedSizeChunkingStrategy(
                ChunkingProperties properties
        ) {
        this.properties = properties;
        }

    @Override
    public List<CodeChunk> chunk(SourceDocument sourceDocument) {

        List<CodeChunk> chunks = new ArrayList<>();

        String content = sourceDocument.getContent();

        int chunkIndex = 0;

        int start = 0;

        while (start < content.length()) {

            int end = Math.min(
                    start + properties.getChunkSize(),
                    content.length()
            );

            String chunkContent =
                    content.substring(start, end);

            chunks.add(
                    CodeChunk.builder()
                            .id(UUID.randomUUID())
                            .workspaceId(
                                    sourceDocument
                                            .getSourceFile()
                                            .getWorkspaceId()
                            )
                            .sourceFile(
                                    sourceDocument.getSourceFile()
                            )
                            .content(chunkContent)
                            .embeddingContent(chunkContent)
                            .chunkIndex(chunkIndex)
                            .startOffset(start)
                            .endOffset(end)
                            .build()
            );

            chunkIndex++;

            if (end == content.length()) {
                break;
            }

            start = end - properties.getOverlap();

        }

        return chunks;

    }

}