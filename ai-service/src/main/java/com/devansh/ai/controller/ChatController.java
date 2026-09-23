
package com.devansh.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devansh.ai.dto.chat.ChatRequest;
import com.devansh.ai.dto.chat.ChatResponse;
import com.devansh.ai.service.RagChatService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final RagChatService ragChatService;

    public ChatController(RagChatService ragChatService) {
        this.ragChatService = ragChatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody ChatRequest request) {

        ChatResponse response = ragChatService.chat(
                request.getWorkspaceId(),
                request.getQuestion()
        );

        return ResponseEntity.ok(response);
    }
}
