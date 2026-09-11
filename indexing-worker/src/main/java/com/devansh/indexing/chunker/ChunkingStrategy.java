package com.devansh.indexing.chunker;

import java.util.List;

import com.devansh.indexing.model.CodeChunk;
import com.devansh.indexing.model.SourceDocument;

public interface ChunkingStrategy {

    List<CodeChunk> chunk(SourceDocument sourceDocument);

}