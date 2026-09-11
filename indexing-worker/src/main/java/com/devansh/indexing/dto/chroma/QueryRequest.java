package com.devansh.indexing.dto.chroma;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryRequest {

    @JsonProperty("query_embeddings")
    private List<List<Float>> queryEmbeddings;

    @JsonProperty("n_results")
    private int nResults;

    @JsonProperty("include")
    private List<String> include;

    @JsonProperty("where")
    private Map<String, Object> where;

    public static QueryRequest from(
            String workspaceId,
            float[] embedding,
            int topK,
            List<String> include
    ) {

        List<Float> embeddingList =
                new java.util.ArrayList<>();

        for (float value : embedding) {
            embeddingList.add(value);
        }

        return QueryRequest.builder()
                .queryEmbeddings(
                        List.of(embeddingList)
                )
                .nResults(topK)
                .include(include)
                .where(
                        Map.of(
                                "workspaceId",
                                workspaceId
                        )
                )
                .build();
    }
}