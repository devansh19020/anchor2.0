package com.devansh.indexing.vectorstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.devansh.indexing.model.CodeChunk;
import com.devansh.indexing.model.SourceFile;

@Component
public class ChromaDocumentMapper {

    public MappedChromaDocuments map(List<CodeChunk> chunks) {

        List<String> ids = new ArrayList<>();
        List<String> documents = new ArrayList<>();
        List<float[]> embeddings = new ArrayList<>();
        List<Map<String, Object>> metadatas = new ArrayList<>();

        for (CodeChunk chunk : chunks) {

            ids.add(chunk.getId().toString());

            documents.add(chunk.getContent());

            embeddings.add(
                    chunk.getEmbedding()
                            .getVector()
            );

            metadatas.add(
                    createMetadata(chunk)
            );

        }

        return MappedChromaDocuments.builder()
                .ids(ids)
                .documents(documents)
                .embeddings(embeddings)
                .metadatas(metadatas)
                .build();

    }

    private Map<String, Object> createMetadata(CodeChunk chunk) {

        SourceFile file = chunk.getSourceFile();

        Map<String, Object> metadata = new HashMap<>();

        metadata.put("workspaceId", chunk.getWorkspaceId().toString());
        metadata.put("chunkId", chunk.getId().toString());

        metadata.put("fileName", file.getFileName());
        metadata.put("language", file.getLanguage().name());

        metadata.put("packageName", file.getPackageName());
        metadata.put("className", file.getClassName());

        metadata.put("chunkIndex", chunk.getChunkIndex());

        metadata.put("startOffset", chunk.getStartOffset());
        metadata.put("endOffset", chunk.getEndOffset());

        return metadata;

    }

}