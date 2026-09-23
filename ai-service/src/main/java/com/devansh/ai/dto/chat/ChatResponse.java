package com.devansh.ai.dto.chat;


import java.util.List;

public class ChatResponse {

    private String answer;

    private List<String> sources;

    // Default constructor
    public ChatResponse() {
    }

    // All-args constructor
    public ChatResponse(String answer, List<String> sources) {
        this.answer = answer;
        this.sources = sources;
    }

    // Private constructor for builder
    private ChatResponse(Builder builder) {
        this.answer = builder.answer;
        this.sources = builder.sources;
    }

    // Getters
    public String getAnswer() {
        return answer;
    }

    public List<String> getSources() {
        return sources;
    }

    // Setters
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    // Static builder() method
    public static Builder builder() {
        return new Builder();
    }

    // toString()
    @Override
    public String toString() {
        return "ChatResponse{" +
                "answer='" + answer + '\'' +
                ", sources=" + sources +
                '}';
    }

    // equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatResponse that = (ChatResponse) o;

        if (answer != null ? !answer.equals(that.answer) : that.answer != null) return false;
        return sources != null ? sources.equals(that.sources) : that.sources == null;
    }

    // hashCode()
    @Override
    public int hashCode() {
        int result = answer != null ? answer.hashCode() : 0;
        result = 31 * result + (sources != null ? sources.hashCode() : 0);
        return result;
    }

    // Builder class
    public static class Builder {

        private String answer;
        private List<String> sources;

        public Builder() {
        }

        public Builder answer(String answer) {
            this.answer = answer;
            return this;
        }

        public Builder sources(List<String> sources) {
            this.sources = sources;
            return this;
        }

        public ChatResponse build() {
            return new ChatResponse(this);
        }
    }
}