package com.devansh.indexing.embedding;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.devansh.indexing.model.CodeChunk;
import com.devansh.indexing.model.VectorEmbedding;
import com.devansh.indexing.properties.GeminiProperties;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;

@Component
public class GeminiEmbeddingModel implements EmbeddingModel{
    private final GoogleAiEmbeddingModel embeddingModel;

    private final GeminiProperties properties;

    public GeminiEmbeddingModel(
            GoogleAiEmbeddingModel embeddingModel,
            GeminiProperties properties
    ) {

        this.embeddingModel = embeddingModel;
        this.properties = properties;

    }

    @Override
    public void embed(CodeChunk chunk) {

        Embedding embedding =
                embeddingModel.embed(chunk.getEmbeddingContent())
                        .content();

        chunk.setEmbedding(

                VectorEmbedding.builder()

                        .vector(
                                embedding.vector()
                        )

                        .dimensions(
                                embedding.vector().length
                        )

                        .model(
                                properties.getEmbeddingModel()
                        )

                        .createdAt(
                                Instant.now()
                        )

                        .build()

        );

    }

}