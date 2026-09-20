package com.devansh.indexing.vectorstore;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.devansh.indexing.dto.chroma.AddEmbeddingsRequest;
import com.devansh.indexing.dto.chroma.CreateCollectionRequest;
import com.devansh.indexing.dto.chroma.CreateCollectionResponse;
import com.devansh.indexing.dto.chroma.QueryRequest;
import com.devansh.indexing.dto.chroma.QueryResponse;
import com.devansh.indexing.properties.ChromaProperties;
import com.devansh.indexing.util.ChromaConstants;

@Component
public class ChromaClient {

    private final RestClient restClient;

    private final ChromaProperties properties;

    public ChromaClient(
            RestClient.Builder builder,
            ChromaProperties properties
    ) {

        this.properties = properties;

        this.restClient =
                builder
                        .baseUrl(
                                "http://"
                                        + properties.getHost()
                                        + ":"
                                        + properties.getPort()
                        )
                        .build();

    }

    public CreateCollectionResponse createCollection(String collectionName) {

        CreateCollectionRequest request =
                new CreateCollectionRequest(
                        collectionName,
                        true
                );

        return restClient.post()

                .uri(
                        "/api/v2/tenants/{tenant}/databases/{database}/collections",
                        ChromaConstants.DEFAULT_TENANT,
                        ChromaConstants.DEFAULT_DATABASE
                )

                .body(request)

                .retrieve()

                .body(CreateCollectionResponse.class);

    }

        public void add(
                String collectionId,
                AddEmbeddingsRequest request
        ) {

        restClient.post()

                .uri(
                        "/api/v2/tenants/{tenant}/databases/{database}/collections/{collectionId}/add",
                        ChromaConstants.DEFAULT_TENANT,
                        ChromaConstants.DEFAULT_DATABASE,
                        collectionId
                )

                .body(request)

                .retrieve()

                .toBodilessEntity();

        }

        public Integer count(String collectionId) {

        return restClient.get()

                .uri(
                        "/api/v2/tenants/{tenant}/databases/{database}/collections/{collectionId}/count",
                        ChromaConstants.DEFAULT_TENANT,
                        ChromaConstants.DEFAULT_DATABASE,
                        collectionId
                )

                .retrieve()

                .body(Integer.class);

        }

        public void deleteCollection(String collectionName) {

        restClient.delete()

                .uri(
                        "/api/v2/tenants/{tenant}/databases/{database}/collections/{collectionName}",
                        ChromaConstants.DEFAULT_TENANT,
                        ChromaConstants.DEFAULT_DATABASE,
                        collectionName
                )

                .retrieve()

                .toBodilessEntity();

        }

        public QueryResponse query(
                String collectionId,
                QueryRequest request
        ) {

        return restClient.post()

                .uri(
                        "/api/v2/tenants/{tenant}/databases/{database}/collections/{collectionId}/query",
                        ChromaConstants.DEFAULT_TENANT,
                        ChromaConstants.DEFAULT_DATABASE,
                        collectionId
                )

                .body(request)

                .retrieve()

                .body(QueryResponse.class);

        }

}