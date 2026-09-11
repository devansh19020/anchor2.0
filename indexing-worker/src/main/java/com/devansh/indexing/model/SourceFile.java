package com.devansh.indexing.model;

import java.nio.file.Path;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SourceFile {

    private UUID id;

    private UUID workspaceId;

    private String fileName;

    private Path relativePath;

    private Language language;

    private String packageName;

    private String className;

}