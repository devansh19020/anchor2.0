package com.devansh.ai.embedding;

public interface QueryEmbeddingModel {

    float[] embedQuery(String query);

}