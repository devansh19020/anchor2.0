package com.devansh.ai.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WorkspaceAccessService {

    private final RestClient restClient;

    public WorkspaceAccessService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8082")
                .build();
    }

    public boolean canAccessWorkspace(String workspaceId, String userId) {
        Boolean result = restClient.get()
                .uri("/api/repositories/workspaces/{workspaceId}/access",
                        workspaceId)
                .header("X-User-Id", userId)
                .retrieve()
                .body(Boolean.class);

        return Boolean.TRUE.equals(result);
    }
}