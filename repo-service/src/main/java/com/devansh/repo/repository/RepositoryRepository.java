package com.devansh.repo.repository;

import com.devansh.repo.model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RepositoryRepository
        extends JpaRepository<Repository, UUID> {

    List<Repository> findByUserId(UUID userId);

    boolean existsByUserIdAndRepositoryUrl(
            UUID userId,
            String repositoryUrl
    );
}