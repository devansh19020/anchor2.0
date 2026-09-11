package com.devansh.indexing.github;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.devansh.indexing.model.Workspace;
import com.devansh.indexing.properties.GithubProperties;

@Component
public class GithubDownloader {

    private final RestClient restClient;
    private final GithubProperties githubProperties;

    public GithubDownloader(
            RestClient.Builder builder,
            GithubProperties properties
    ) {
        this.githubProperties = properties;

        // 1. Configure the Java HttpClient to FOLLOW redirect responses (like 301, 302)
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        // 2. Wrap it in a Spring RequestFactory
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);

        // 3. Build the RestClient using this factory
        this.restClient = builder
                .requestFactory(requestFactory)
                .baseUrl(properties.getApi().getBaseUrl())
                // GitHub strongly recommends these headers
                .defaultHeader("Accept", "application/vnd.github+json")
                .defaultHeader("User-Agent", "Anchor-App")
                .build();
    }

    public Path downloadRepository(
            Workspace workspace,
            String owner,
            String repository,
            String branch
    ) {
        Path zipPath = workspace.getRootPath().resolve("repository.zip");
        

        Resource resource = restClient.get()
                .uri("/repos/{owner}/{repo}/zipball/{branch}", owner, repository, branch)
                // If you are downloading private repositories, you must add your Personal Access Token (PAT):
                .header("Authorization", "Bearer " + githubProperties.getToken())
                .retrieve()
                .body(Resource.class);

        if (resource == null) {
            throw new RuntimeException("Failed to download repository: Response body was null.");
        }

        try (InputStream inputStream = resource.getInputStream()) {
            Files.copy(inputStream, zipPath, StandardCopyOption.REPLACE_EXISTING);
            return zipPath;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to save ZIP file.", ex);
        }
    }
}