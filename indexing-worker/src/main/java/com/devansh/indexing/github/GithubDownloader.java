package com.devansh.indexing.github;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.devansh.indexing.exception.RepositoryDownloadException;
import com.devansh.indexing.properties.GithubProperties;
import com.devansh.indexing.workspace.Workspace;

@Component
public class GithubDownloader {

    private final RestClient restClient;
    private final GithubProperties githubProperties;

    public GithubDownloader(
            RestClient.Builder builder,
            GithubProperties properties
    ) {

        this.githubProperties = properties;

        HttpClient httpClient =
                HttpClient.newBuilder()
                        .followRedirects(
                                HttpClient.Redirect.NORMAL
                        )
                        .build();

        JdkClientHttpRequestFactory requestFactory =
                new JdkClientHttpRequestFactory(
                        httpClient
                );

        this.restClient = builder
                .requestFactory(requestFactory)
                .baseUrl(
                        properties.getApi().getBaseUrl()
                )
                .defaultHeader(
                        "Accept",
                        "application/vnd.github+json"
                )
                .defaultHeader(
                        "User-Agent",
                        "Anchor-App"
                )
                .build();
    }

    public Path downloadRepository(
            Workspace workspace,
            String owner,
            String repository,
            String branch
    ) {

        Path zipPath =
                workspace.getRootPath()
                        .resolve("repository.zip");

        try {

            Resource resource =
                    restClient.get()
                            .uri(
                                    "/repos/{owner}/{repo}/zipball/{branch}",
                                    owner,
                                    repository,
                                    branch
                            )
                            .headers(headers -> {

                                String token =
                                        githubProperties.getToken();

                                if (token != null &&
                                        !token.isBlank()) {

                                    headers.setBearerAuth(token);
                                }
                            })
                            .retrieve()

                            .onStatus(
                                    HttpStatusCode::isError,
                                    (request, response) -> {

                                        throw new RepositoryDownloadException(
                                                "Failed to download GitHub repository: "
                                                        + owner
                                                        + "/"
                                                        + repository
                                                        + " - "
                                                        + response.getStatusCode()
                                        );
                                    }
                            )

                            .body(Resource.class);

            if (resource == null) {

                throw new RepositoryDownloadException(
                        "GitHub returned an empty response"
                );
            }

            Files.createDirectories(
                    workspace.getRootPath()
            );

            try (InputStream inputStream =
                         resource.getInputStream()) {

                Files.copy(
                        inputStream,
                        zipPath,
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

            return zipPath;

        } catch (IOException ex) {

            throw new RepositoryDownloadException(
                    "Failed to save repository ZIP",
                    ex
            );
        }
    }
}