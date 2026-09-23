package com.devansh.ai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devansh.ai.chat.AiChatModel;
import com.devansh.ai.dto.chat.ChatResponse;
import com.devansh.ai.model.RetrievedChunk;
import com.devansh.ai.prompt.PromptBuilder;

@Service
public class RagChatService {

    private final SearchService searchService;

    private final PromptBuilder promptBuilder;

    private final AiChatModel chatModel;

    public RagChatService(

            SearchService searchService,

            PromptBuilder promptBuilder,

            AiChatModel chatModel

    ) {

        this.searchService = searchService;
        this.promptBuilder = promptBuilder;
        this.chatModel = chatModel;

    }

    public ChatResponse chat(
            String workspaceId,
            String question
    ) {

        List<RetrievedChunk> chunks =
                searchService.search(workspaceId, question);

        String prompt =
                promptBuilder.build(
                        question,
                        chunks
                );

        String answer =
                chatModel.chat(
                        prompt
                );

        List<String> sources =
                chunks.stream()

                        .map(RetrievedChunk::getFileName)

                        .distinct()

                        .toList();

        return ChatResponse.builder()

                .answer(answer)

                .sources(sources)

                .build();

    }

}