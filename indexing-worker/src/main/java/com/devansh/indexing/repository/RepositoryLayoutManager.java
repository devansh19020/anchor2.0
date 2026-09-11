package com.devansh.indexing.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import com.devansh.indexing.model.Workspace;

@Component
public class RepositoryLayoutManager {

    public Path normalizeRepository(
            Workspace workspace,
            Path extractionDirectory
    ) {

        try {

            Path sourceDirectory =
                    workspace.getSourcePath();

            Files.createDirectories(sourceDirectory);

            Path githubRoot =
                    Files.list(extractionDirectory)
                            .filter(Files::isDirectory)
                            .findFirst()
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Repository root not found."
                                    ));

            Files.list(githubRoot)
                    .forEach(path -> {

                        try {

                            Files.move(
                                    path,
                                    sourceDirectory.resolve(
                                            path.getFileName()
                                    )
                            );

                        } catch (IOException ex) {

                            throw new RuntimeException(ex);

                        }

                    });

            return sourceDirectory;

        } catch (IOException ex) {

            throw new RuntimeException(
                    "Failed to normalize repository.",
                    ex
            );

        }

    }

}