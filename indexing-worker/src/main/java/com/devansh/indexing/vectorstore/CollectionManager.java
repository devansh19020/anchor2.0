package com.devansh.indexing.vectorstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.devansh.indexing.dto.chroma.CreateCollectionResponse;
import com.devansh.indexing.properties.ChromaProperties;

import jakarta.annotation.PostConstruct;

@Component
public class CollectionManager {

    private final ChromaClient chromaClient;

    private final ChromaProperties properties;

    private static final Logger log =
        LoggerFactory.getLogger(CollectionManager.class);

    private String collectionId;

    public CollectionManager(
            ChromaClient chromaClient,
            ChromaProperties properties
    ) {
        this.chromaClient = chromaClient;
        this.properties = properties;
    }

    @PostConstruct
    public void initialize() {

        CreateCollectionResponse response =
                chromaClient.createCollection(
                        properties.getCollection().getName()
                );

        this.collectionId = response.getId();

        log.info(
                "Chroma collection initialized: {} ({})",
                properties.getCollection().getName(),
                collectionId
        );
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void recreateCollection() {

        chromaClient.deleteCollection(
                properties.getCollection().getName()
        );

        initialize();

    }

}