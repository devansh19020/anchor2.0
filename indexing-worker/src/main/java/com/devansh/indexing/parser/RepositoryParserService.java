package com.devansh.indexing.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.devansh.indexing.model.SourceDocument;
import com.devansh.indexing.workspace.Workspace;

@Service
public class RepositoryParserService {

    private static final List<String> SUPPORTED_EXTENSIONS = List.of(
            ".java",
            ".kt",
            ".js",
            ".ts",
            ".xml",
            ".yml",
            ".yaml",
            ".md"
    );

    private static final List<String> IGNORED_DIRECTORIES = List.of(
            ".git",
            "target",
            "build",
            "node_modules",
            ".idea",
            ".gradle"
    );

    private final ParserFactory parserFactory;

    public RepositoryParserService(ParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    public List<SourceDocument> parseRepository(Workspace workspace) {

        Path repositoryRoot = workspace.getSourcePath();

        if (!Files.exists(repositoryRoot)) {
            throw new RuntimeException(
                    "Repository source directory does not exist: "
                            + repositoryRoot
            );
        }

        List<SourceDocument> documents = new ArrayList<>();

        try {
            Files.walk(repositoryRoot)
                    .filter(Files::isRegularFile)
                    .filter(this::isSupportedFile)
                    .filter(path -> !isInsideIgnoredDirectory(path, repositoryRoot))
                    .forEach(file -> {

                        try {
                            CodeParser parser =
                                    parserFactory.getParser(
                                            file.getFileName().toString()
                                    );

                            SourceDocument document =
                                    parser.parse(
                                            workspace.getWorkspaceId(),
                                            repositoryRoot,
                                            file
                                    );

                            documents.add(document);

                        } catch (IOException e) {
                            throw new RuntimeException(
                                    "Failed to parse file: " + file,
                                    e
                            );
                        }
                    });

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to scan repository: "
                            + repositoryRoot,
                    e
            );
        }

        return documents;
    }

    private boolean isSupportedFile(Path file) {

        String fileName =
                file.getFileName()
                        .toString()
                        .toLowerCase();

        return SUPPORTED_EXTENSIONS.stream()
                .anyMatch(fileName::endsWith);
    }

    private boolean isInsideIgnoredDirectory(
            Path file,
            Path repositoryRoot
    ) {

        Path relativePath =
                repositoryRoot.relativize(file);

        for (Path part : relativePath) {

            if (IGNORED_DIRECTORIES.contains(
                    part.toString()
            )) {
                return true;
            }
        }

        return false;
    }
}