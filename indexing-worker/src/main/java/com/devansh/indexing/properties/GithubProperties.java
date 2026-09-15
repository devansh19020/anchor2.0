package com.devansh.indexing.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github")
public class GithubProperties {

    private Api api = new Api();

    private String token;

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class Api {

        private String baseUrl;

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }
}