package com.devansh.ai.model;

import java.util.Objects;

public class RetrievedChunk {

    private String content;

    private double score;

    private String workspaceId;

    private String chunkId;

    private String fileName;

    private String packageName;

    private String className;

    private String language;

    private Integer chunkIndex;

    // Default constructor
    public RetrievedChunk() {
    }

    // All-args constructor
    public RetrievedChunk(String content, double score, String workspaceId, String chunkId,
                          String fileName, String packageName, String className,
                          String language, Integer chunkIndex) {
        this.content = content;
        this.score = score;
        this.workspaceId = workspaceId;
        this.chunkId = chunkId;
        this.fileName = fileName;
        this.packageName = packageName;
        this.className = className;
        this.language = language;
        this.chunkIndex = chunkIndex;
    }

    // Private constructor for builder
    private RetrievedChunk(Builder builder) {
        this.content = builder.content;
        this.score = builder.score;
        this.workspaceId = builder.workspaceId;
        this.chunkId = builder.chunkId;
        this.fileName = builder.fileName;
        this.packageName = builder.packageName;
        this.className = builder.className;
        this.language = builder.language;
        this.chunkIndex = builder.chunkIndex;
    }

    // Getters
    public String getContent() {
        return content;
    }

    public double getScore() {
        return score;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public String getChunkId() {
        return chunkId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getChunkIndex() {
        return chunkIndex;
    }

    // Static builder() method
    public static Builder builder() {
        return new Builder();
    }

    // toString()
    @Override
    public String toString() {
        return "RetrievedChunk{" +
                "content='" + content + '\'' +
                ", score=" + score +
                ", workspaceId='" + workspaceId + '\'' +
                ", chunkId='" + chunkId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", language='" + language + '\'' +
                ", chunkIndex=" + chunkIndex +
                '}';
    }

    // equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RetrievedChunk that = (RetrievedChunk) o;

        if (Double.compare(that.score, score) != 0) return false;
        if (!Objects.equals(content, that.content)) return false;
        if (!Objects.equals(workspaceId, that.workspaceId)) return false;
        if (!Objects.equals(chunkId, that.chunkId)) return false;
        if (!Objects.equals(fileName, that.fileName)) return false;
        if (!Objects.equals(packageName, that.packageName)) return false;
        if (!Objects.equals(className, that.className)) return false;
        if (!Objects.equals(language, that.language)) return false;
        return Objects.equals(chunkIndex, that.chunkIndex);
    }

    // hashCode()
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = content != null ? content.hashCode() : 0;
        temp = Double.doubleToLongBits(score);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (workspaceId != null ? workspaceId.hashCode() : 0);
        result = 31 * result + (chunkId != null ? chunkId.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (packageName != null ? packageName.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (chunkIndex != null ? chunkIndex.hashCode() : 0);
        return result;
    }

    // Builder class
    public static class Builder {

        private String content;
        private double score;
        private String workspaceId;
        private String chunkId;
        private String fileName;
        private String packageName;
        private String className;
        private String language;
        private Integer chunkIndex;

        public Builder() {
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder score(double score) {
            this.score = score;
            return this;
        }

        public Builder workspaceId(String workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder chunkId(String chunkId) {
            this.chunkId = chunkId;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder className(String className) {
            this.className = className;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder chunkIndex(Integer chunkIndex) {
            this.chunkIndex = chunkIndex;
            return this;
        }

        public RetrievedChunk build() {
            return new RetrievedChunk(this);
        }
    }
}