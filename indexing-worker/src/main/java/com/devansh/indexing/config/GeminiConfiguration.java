package com.devansh.indexing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.devansh.indexing.properties.GeminiProperties;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

@Configuration
public class GeminiConfiguration {

    private final GeminiProperties properties;

    public GeminiConfiguration(GeminiProperties properties) {
        this.properties = properties;
    }

    @Bean
    public GoogleAiEmbeddingModel embeddingModel() {

        return GoogleAiEmbeddingModel.builder()

                .apiKey(properties.getApiKey())

                .modelName(properties.getEmbeddingModel())

                .build();

    }

    @Bean
    public ChatModel chatModel() {

        return GoogleAiGeminiChatModel.builder()

                .apiKey(properties.getApiKey())

                .modelName(properties.getChatModel())

                .temperature(0.1)

                .build();

    }

}
