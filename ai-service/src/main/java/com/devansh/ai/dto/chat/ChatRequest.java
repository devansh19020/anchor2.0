package com.devansh.ai.dto.chat;

import jakarta.validation.constraints.NotBlank;

public class ChatRequest {

    @NotBlank
    private String question;

    @NotBlank
    private String workspaceId;

    // Default constructor
    public ChatRequest() {
    }

    // All-args constructor
    public ChatRequest(String question, String workspaceId) {
        this.question = question;
        this.workspaceId = workspaceId;
    }

    // Getters
    public String getQuestion() {
        return question;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    // Setters
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    // toString()
    @Override
    public String toString() {
        return "ChatRequest{" +
                "question='" + question + '\'' +
                ", workspaceId='" + workspaceId + '\'' +
                '}';
    }

    // equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatRequest that = (ChatRequest) o;

        if (question != null ? !question.equals(that.question) : that.question != null) return false;
        return workspaceId != null ? workspaceId.equals(that.workspaceId) : that.workspaceId == null;
    }

    // hashCode()
    @Override
    public int hashCode() {
        int result = question != null ? question.hashCode() : 0;
        result = 31 * result + (workspaceId != null ? workspaceId.hashCode() : 0);
        return result;
    }
}