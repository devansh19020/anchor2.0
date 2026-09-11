package com.devansh.repo.github;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GithubUrlValidator {

    private static final Pattern GITHUB_URL_PATTERN =
            Pattern.compile(
                    "^https://github\\.com/([A-Za-z0-9_.-]+)/([A-Za-z0-9_.-]+?)(?:\\.git)?/?$"
            );

    public GithubRepositoryInfo parse(String url) {

        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException(
                    "GitHub repository URL cannot be empty"
            );
        }

        Matcher matcher = GITHUB_URL_PATTERN.matcher(url.trim());

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid GitHub repository URL"
            );
        }

        return new GithubRepositoryInfo(
                matcher.group(1),
                matcher.group(2)
        );
    }

    public boolean isValidGithubUrl(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }

        return GITHUB_URL_PATTERN
                .matcher(url.trim())
                .matches();
    }

    public String extractOwner(String url) {
        return parse(url).owner();
    }

    public String extractRepository(String url) {
        return parse(url).repositoryName();
    }

    public record GithubRepositoryInfo(
            String owner,
            String repositoryName
    ) {
    }
}