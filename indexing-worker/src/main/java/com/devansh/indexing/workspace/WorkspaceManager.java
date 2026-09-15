package com.devansh.indexing.workspace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.devansh.indexing.exception.WorkspaceCreationException;
import com.devansh.indexing.properties.WorkspaceProperties;

@Component
public class WorkspaceManager {

    private final WorkspaceProperties workspaceProperties;

    public WorkspaceManager(
            WorkspaceProperties workspaceProperties
    ) {
        this.workspaceProperties = workspaceProperties;
    }

    public Workspace createWorkspace(UUID workspaceId) {

        try {

            Path rootDirectory =
                    Path.of(workspaceProperties.getRootDirectory());

            Files.createDirectories(rootDirectory);

            Path workspacePath =
                    rootDirectory.resolve(workspaceId.toString());

            Files.createDirectories(workspacePath);

            return Workspace.builder()
                    .workspaceId(workspaceId)
                    .rootPath(workspacePath)
                    .sourcePath(workspacePath.resolve("source"))
                    .createdAt(Instant.now())
                    .build();

        } catch (IOException ex) {

            throw new WorkspaceCreationException(
                    "Failed to create workspace.",
                    ex
            );
        }
    }

}