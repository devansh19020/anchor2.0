package com.devansh.indexing.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import com.devansh.indexing.model.SourceDocument;

public interface CodeParser {

    SourceDocument parse(
            UUID workspaceId,
            Path repositoryRoot,
            Path file
    ) throws IOException;

}