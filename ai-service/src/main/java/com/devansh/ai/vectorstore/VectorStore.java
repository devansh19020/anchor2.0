package com.devansh.ai.vectorstore;

import java.util.List;

import com.devansh.ai.model.RetrievedChunk;

public interface VectorStore {

    List<RetrievedChunk> search(
            String workspaceId,
            float[] queryEmbedding,
            int topK
    );

}