package com.devansh.ai.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.devansh.ai.dto.chat.ChatRequest;
import com.devansh.ai.dto.chat.ChatResponse;
import com.devansh.ai.service.RagChatService;
import com.devansh.ai.service.WorkspaceAccessService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final RagChatService ragChatService;
    private final WorkspaceAccessService workspaceAccessService;

    public ChatController(
            RagChatService ragChatService,
            WorkspaceAccessService workspaceAccessService) {
        this.ragChatService = ragChatService;
        this.workspaceAccessService = workspaceAccessService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> chat(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody ChatRequest request) {

        boolean allowed = workspaceAccessService.canAccessWorkspace(
                request.getWorkspaceId(), userId);

        if (!allowed) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You do not have access to this workspace");
        }

        ChatResponse response = ragChatService.chat(
                request.getWorkspaceId(),
                request.getQuestion());

        return ResponseEntity.ok(response);
    }
}