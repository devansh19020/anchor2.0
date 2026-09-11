package com.devansh.indexing.dto.chroma;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddEmbeddingsRequest {

    private List<String> ids;

    private List<String> documents;

    private List<float[]> embeddings;

    private List<Map<String, Object>> metadatas;

}