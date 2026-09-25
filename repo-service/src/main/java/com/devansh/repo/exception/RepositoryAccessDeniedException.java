package com.devansh.repo.exception;

public class RepositoryAccessDeniedException extends RuntimeException {

    public RepositoryAccessDeniedException(String message) {
        super(message);
    }
}