package com.devansh.indexing.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Component;

import com.devansh.indexing.model.Workspace;

@Component
public class ZipExtractor {

    public Path extract(
            Workspace workspace,
            Path zipFile
    ) {

        Path extractionDirectory =
                workspace.getRootPath()
                        .resolve("extracted");

        try {

            Files.createDirectories(extractionDirectory);

            try (ZipInputStream zipInputStream =
                         new ZipInputStream(
                                 Files.newInputStream(zipFile)
                         )) {

                ZipEntry entry;

                while ((entry = zipInputStream.getNextEntry()) != null) {

                    Path target =
                            extractionDirectory
                                    .resolve(entry.getName())
                                    .normalize();

                    if (!target.startsWith(extractionDirectory)) {
                        throw new RuntimeException(
                                "Blocked Zip Slip attack."
                        );
                    }

                    if (entry.isDirectory()) {

                        Files.createDirectories(target);

                    } else {

                        Files.createDirectories(target.getParent());

                        Files.copy(
                                zipInputStream,
                                target,
                                StandardCopyOption.REPLACE_EXISTING
                        );

                    }

                    zipInputStream.closeEntry();

                }

            }

            return extractionDirectory;

        } catch (IOException ex) {

            throw new RuntimeException(
                    "Failed to extract repository.",
                    ex
            );

        }

    }

}