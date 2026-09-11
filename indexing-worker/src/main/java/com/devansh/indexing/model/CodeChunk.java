package com.devansh.indexing.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodeChunk {

    private UUID id;

    private UUID workspaceId;

    private SourceFile sourceFile;

    private String content;

    private int chunkIndex;

    private int startOffset;

    private int endOffset;

    private VectorEmbedding embedding;

    private String embeddingContent;

}