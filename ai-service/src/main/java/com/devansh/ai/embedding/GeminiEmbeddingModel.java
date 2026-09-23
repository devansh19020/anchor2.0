package com.devansh.ai.embedding;


import org.springframework.stereotype.Component;

import com.devansh.ai.properties.GeminiProperties;

import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;

@Component
public class GeminiEmbeddingModel implements QueryEmbeddingModel {
    private final GoogleAiEmbeddingModel embeddingModel;

    public GeminiEmbeddingModel(
            GoogleAiEmbeddingModel embeddingModel,
            GeminiProperties properties
    ) {

        this.embeddingModel = embeddingModel;

    }

        @Override
        public float[] embedQuery(String query) {

        return embeddingModel
                .embed(query)
                .content()
                .vector();

        }

}