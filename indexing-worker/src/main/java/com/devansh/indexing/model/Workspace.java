package com.devansh.indexing.model;

import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Workspace {

    private UUID workspaceId;

    private Path rootPath;

    private Path sourcePath;

    private Instant createdAt;

}