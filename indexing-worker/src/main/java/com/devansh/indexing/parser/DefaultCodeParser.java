package com.devansh.indexing.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.devansh.indexing.model.Language;
import com.devansh.indexing.model.SourceDocument;
import com.devansh.indexing.model.SourceFile;

@Component
public class DefaultCodeParser implements CodeParser {

    @Override
    public SourceDocument parse(
            UUID workspaceId,
            Path repositoryRoot,
            Path file
    ) throws IOException {

        String content = Files.readString(file);

        String fileName = file.getFileName().toString();

        Path relativePath = repositoryRoot.relativize(file);

        SourceFile sourceFile = SourceFile.builder()
                .id(UUID.randomUUID())
                .workspaceId(workspaceId)
                .fileName(fileName)
                .relativePath(relativePath)
                .language(detectLanguage(fileName))
                .packageName(extractPackage(content))
                .className(extractClassName(fileName))
                .build();

        return SourceDocument.builder()
                .sourceFile(sourceFile)
                .content(content)
                .build();
    }

    private Language detectLanguage(String fileName) {

        if (fileName.endsWith(".java")) return Language.JAVA;

        if (fileName.endsWith(".kt")) return Language.KOTLIN;

        if (fileName.endsWith(".js")) return Language.JAVASCRIPT;

        if (fileName.endsWith(".ts")) return Language.TYPESCRIPT;

        if (fileName.endsWith(".xml")) return Language.XML;

        if (fileName.endsWith(".yml")
                || fileName.endsWith(".yaml")) {
            return Language.YAML;
        }

        if (fileName.endsWith(".md")) return Language.MARKDOWN;

        return Language.UNKNOWN;
    }

    private String extractPackage(String content) {

        for (String line : content.split("\n")) {

            line = line.trim();

            if (line.startsWith("package ")) {

                return line
                        .replace("package", "")
                        .replace(";", "")
                        .trim();

            }

        }

        return "";
    }

    private String extractClassName(String fileName) {

        int index = fileName.lastIndexOf('.');

        if (index == -1) {
            return fileName;
        }

        return fileName.substring(0, index);
    }

}