package com.devansh.ai.vectorstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.devansh.ai.dto.chroma.QueryResponse;
import com.devansh.ai.model.RetrievedChunk;

@Component
public class QueryResultMapper {

    public List<RetrievedChunk> map(QueryResponse response) {

        List<RetrievedChunk> results = new ArrayList<>();

        if (response == null
                || response.getDocuments() == null
                || response.getDocuments().isEmpty()) {
            return results;
        }

        List<String> documents = response.getDocuments().get(0);
        List<Double> distances = response.getDistances().get(0);
        List<Map<String, Object>> metadatas = response.getMetadatas().get(0);

        for (int i = 0; i < documents.size(); i++) {

            Map<String, Object> metadata = metadatas.get(i);

            RetrievedChunk chunk =
                    RetrievedChunk.builder()
                            .content(documents.get(i))
                            .score(Math.round(distanceToSimilarity(distances.get(i)) * 1000) / 1000.0)
                            .workspaceId(
                                    asString(metadata.get("workspaceId"))
                            )
                            .chunkId(
                                    asString(metadata.get("chunkId"))
                            )
                            .fileName(
                                    asString(metadata.get("fileName"))
                            )
                            .packageName(
                                    asString(metadata.get("packageName"))
                            )
                            .className(
                                    asString(metadata.get("className"))
                            )
                            .language(
                                    asString(metadata.get("language"))
                            )
                            .chunkIndex(
                                    asInteger(metadata.get("chunkIndex"))
                            )
                            .build();

            results.add(chunk);

        }

        return results;

    }

    private String asString(Object value) {

        return value == null
                ? null
                : value.toString();

    }

    private Integer asInteger(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Number number) {
            return number.intValue();
        }

        return Integer.parseInt(value.toString());

    }

    private double distanceToSimilarity(
            double distance
    ) {

        return 1.0 / (1.0 + distance);

    }

}