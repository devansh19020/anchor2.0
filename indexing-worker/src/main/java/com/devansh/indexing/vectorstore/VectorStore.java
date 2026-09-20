package com.devansh.indexing.vectorstore;

import java.util.List;

import com.devansh.indexing.model.CodeChunk;

public interface VectorStore {

    int count();

    void store(List<CodeChunk> chunks);

}