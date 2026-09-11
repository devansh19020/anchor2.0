package com.devansh.indexing.vectorstore;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MappedChromaDocuments {

    private final List<String> ids;

    private final List<String> documents;

    private final List<float[]> embeddings;

    private final List<Map<String, Object>> metadatas;

}