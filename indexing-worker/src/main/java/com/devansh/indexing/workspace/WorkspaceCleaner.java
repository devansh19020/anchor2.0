package com.devansh.indexing.workspace;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import org.springframework.stereotype.Component;

@Component
public class WorkspaceCleaner {

    public void delete(Path path) {

        if (path == null || !Files.exists(path)) {
            return;
        }

        try {

            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .forEach(file -> {

                        try {

                            Files.deleteIfExists(file);

                        } catch (IOException ex) {

                            throw new RuntimeException(ex);

                        }

                    });

        } catch (IOException ex) {

            throw new RuntimeException(
                    "Failed to delete " + path,
                    ex
            );

        }

    }

}