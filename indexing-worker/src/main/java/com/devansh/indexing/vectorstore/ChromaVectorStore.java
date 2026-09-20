package com.devansh.indexing.vectorstore;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devansh.indexing.dto.chroma.AddEmbeddingsRequest;
import com.devansh.indexing.model.CodeChunk;

@Component
public class ChromaVectorStore implements VectorStore {

    private final ChromaClient chromaClient;

    private final ChromaDocumentMapper documentMapper;

    private final CollectionManager collectionManager;

    public ChromaVectorStore(
            ChromaClient chromaClient,
            ChromaDocumentMapper documentMapper,
            CollectionManager collectionManager
    ) {

        this.chromaClient = chromaClient;
        this.documentMapper = documentMapper;
        this.collectionManager = collectionManager;

    }

    @Override
    public void store(List<CodeChunk> chunks) {

        if (chunks == null || chunks.isEmpty()) {
            return;
        }

        MappedChromaDocuments mapped =
                documentMapper.map(chunks);

        AddEmbeddingsRequest request =
                AddEmbeddingsRequest.builder()
                        .ids(mapped.getIds())
                        .documents(mapped.getDocuments())
                        .embeddings(mapped.getEmbeddings())
                        .metadatas(mapped.getMetadatas())
                        .build();

        chromaClient.add(
                collectionManager.getCollectionId(),
                request
        );

    }

    @Override
    public int count() {

        return chromaClient.count(
                collectionManager.getCollectionId()
        );

    }

}