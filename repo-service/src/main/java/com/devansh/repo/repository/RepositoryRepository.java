package com.devansh.repo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devansh.repo.model.Repository;

public interface RepositoryRepository
        extends JpaRepository<Repository, UUID> {

    List<Repository> findByUserId(UUID userId);

    boolean existsByUserIdAndRepositoryUrl(
            UUID userId,
            String repositoryUrl
    );

    
}