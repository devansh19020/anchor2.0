package com.devansh.indexing.embedding;

import com.devansh.indexing.model.CodeChunk;

public interface EmbeddingModel {

    void embed(CodeChunk chunk);

}