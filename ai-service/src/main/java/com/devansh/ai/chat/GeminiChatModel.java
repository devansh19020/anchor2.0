package com.devansh.ai.chat;

import org.springframework.stereotype.Component;

import dev.langchain4j.model.chat.ChatModel;

@Component
public class GeminiChatModel implements AiChatModel {

    private final ChatModel model;

    public GeminiChatModel(
            ChatModel model
    ) {
        this.model = model;
    }

    @Override
    public String chat(
            String prompt
    ) {

        return model.chat(prompt);

    }

}