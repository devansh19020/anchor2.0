package com.devansh.repo.exception;

public class RepositoryNotFoundException
        extends RuntimeException {

    public RepositoryNotFoundException(String message) {
        super(message);
    }
}