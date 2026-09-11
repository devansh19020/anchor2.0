package com.devansh.repo.github;

import com.devansh.repo.dto.GithubRepositoryResponse;
import com.devansh.repo.exception.RepositoryNotFoundException;
import com.devansh.repo.properties.GithubProperties;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class GithubClient {

    private final RestClient restClient;
    private final GithubProperties githubProperties;

    public GithubClient(
            RestClient.Builder builder,
            GithubProperties githubProperties
    ) {

        this.githubProperties = githubProperties;

        this.restClient = builder
                .baseUrl(
                        githubProperties
                                .getApi()
                                .getBaseUrl()
                )
                .build();
    }

    public GithubRepositoryResponse fetchRepository(
            String owner,
            String repository
    ) {

        try {

            return restClient.get()

                    .uri(
                            "/repos/{owner}/{repo}",
                            owner,
                            repository
                    )

                    .headers(headers -> {

                        String token =
                                githubProperties.getToken();

                        if (token != null &&
                                !token.isBlank()) {

                            headers.setBearerAuth(token);
                        }

                        headers.set(
                                "Accept",
                                "application/vnd.github+json"
                        );

                    })

                    .retrieve()

                    .onStatus(
                            HttpStatusCode::is4xxClientError,
                            (request, response) -> {

                                throw new RepositoryNotFoundException(
                                        "GitHub repository not found: "
                                                + owner
                                                + "/"
                                                + repository
                                );
                            }
                    )

                    .onStatus(
                            HttpStatusCode::is5xxServerError,
                            (request, response) -> {

                                throw new RuntimeException(
                                        "GitHub server error"
                                );
                            }
                    )

                    .body(
                            GithubRepositoryResponse.class
                    );

        } catch (RestClientResponseException ex) {

            throw new RuntimeException(
                    "GitHub API error: "
                            + ex.getStatusCode(),
                    ex
            );
        }
    }
}