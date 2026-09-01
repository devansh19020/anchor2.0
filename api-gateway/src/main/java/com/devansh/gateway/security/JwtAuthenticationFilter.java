package com.devansh.gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path = exchange.getRequest()
                .getURI()
                .getPath();

        // Public endpoints
        if (isPublicEndpoint(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // No Authorization header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        // Invalid JWT
        if (!jwtService.isValid(token)) {
            return unauthorized(exchange);
        }

        String userId = jwtService.extractUserId(token);
        String role = jwtService.extractRole(token);

        // Add authenticated user information
        ServerWebExchange modifiedExchange = exchange
        .mutate()
        .request(request -> request
                .headers(headers -> {
                    headers.remove("X-User-Id");
                    headers.remove("X-User-Role");

                    headers.add("X-User-Id", userId);
                    headers.add(
                            "X-User-Role",
                            role != null ? role : "USER"
                    );
                })
        )
        .build();

        return chain.filter(modifiedExchange);
    }

    private boolean isPublicEndpoint(String path) {

        return path.equals("/api/users/register")
                || path.equals("/api/users/login")
                || path.equals("/actuator/health");
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        return exchange.getResponse().setComplete();
    }
}