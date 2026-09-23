package com.devansh.ai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devansh.ai.embedding.QueryEmbeddingModel;
import com.devansh.ai.model.RetrievedChunk;
import com.devansh.ai.properties.SearchProperties;
import com.devansh.ai.vectorstore.VectorStore;

@Service
public class SearchService {

    private final SearchProperties properties;

    private final QueryEmbeddingModel embeddingModel;

    private final VectorStore vectorStore;

    public SearchService(
            QueryEmbeddingModel embeddingModel,
            VectorStore vectorStore,
            SearchProperties properties
    ) {

        this.embeddingModel = embeddingModel;
        this.vectorStore = vectorStore;
        this.properties = properties;

    }

    public List<RetrievedChunk> search(
            String workspaceId,
            String question
    ) {

        float[] embedding =
                embeddingModel.embedQuery(
                        question
                );

        List<RetrievedChunk> results =
                vectorStore.search(
                        workspaceId,
                        embedding,
                        properties.topK()
                );

        return results.stream()
                .filter(chunk -> chunk.getScore() >= properties.minimumScore())
                .toList();

        }

}