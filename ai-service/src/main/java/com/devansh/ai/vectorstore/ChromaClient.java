package com.devansh.ai.vectorstore;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.devansh.ai.dto.chroma.CreateCollectionResponse;
import com.devansh.ai.dto.chroma.QueryRequest;
import com.devansh.ai.dto.chroma.QueryResponse;
import com.devansh.ai.properties.ChromaProperties;
import com.devansh.ai.util.ChromaConstants;

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

        public CreateCollectionResponse getCollection(
                String collectionName
        ) {
        return restClient.get()
                .uri(
                        "/api/v2/tenants/{tenant}/databases/{database}/collections/{collectionName}",
                        ChromaConstants.DEFAULT_TENANT,
                        ChromaConstants.DEFAULT_DATABASE,
                        collectionName
                )
                .retrieve()
                .body(CreateCollectionResponse.class);
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

}