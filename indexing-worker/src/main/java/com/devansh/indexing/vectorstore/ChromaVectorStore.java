package com.devansh.indexing.vectorstore;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devansh.indexing.dto.chroma.AddEmbeddingsRequest;
import com.devansh.indexing.dto.chroma.QueryRequest;
import com.devansh.indexing.dto.chroma.QueryResponse;
import com.devansh.indexing.model.CodeChunk;
import com.devansh.indexing.model.RetrievedChunk;
import com.devansh.indexing.util.ChromaConstants;

@Component
public class ChromaVectorStore implements VectorStore {

    private final ChromaClient chromaClient;

    private final ChromaDocumentMapper documentMapper;

    private final CollectionManager collectionManager;

    private final QueryResultMapper mapper;

    public ChromaVectorStore(
            ChromaClient chromaClient,
            ChromaDocumentMapper documentMapper,
            QueryResultMapper mapper,
            CollectionManager collectionManager
    ) {

        this.chromaClient = chromaClient;
        this.documentMapper = documentMapper;
        this.mapper = mapper;
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

        @Override
        public List<RetrievedChunk> search(
                String workspaceId,
                float[] queryEmbedding,
                int topK
        ) {

        QueryRequest request =
                QueryRequest.from(
                        workspaceId,
                        queryEmbedding,
                        topK,
                        ChromaConstants.QUERY_INCLUDE
                );

        QueryResponse response =
                chromaClient.query(
                        collectionManager.getCollectionId(),
                        request
                );

        return mapper.map(response);
        }

}