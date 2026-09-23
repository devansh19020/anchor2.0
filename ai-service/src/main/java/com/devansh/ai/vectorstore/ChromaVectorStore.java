package com.devansh.ai.vectorstore;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devansh.ai.dto.chroma.QueryRequest;
import com.devansh.ai.dto.chroma.QueryResponse;
import com.devansh.ai.model.RetrievedChunk;
import com.devansh.ai.util.ChromaConstants;

@Component
public class ChromaVectorStore implements VectorStore {

    private final ChromaClient chromaClient;

    private final CollectionManager collectionManager;

    private final QueryResultMapper mapper;

    public ChromaVectorStore(
            ChromaClient chromaClient,
            QueryResultMapper mapper,
            CollectionManager collectionManager
    ) {

        this.chromaClient = chromaClient;
        this.mapper = mapper;
        this.collectionManager = collectionManager;

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