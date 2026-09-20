package com.devansh.indexing.embedding;

import org.springframework.stereotype.Component;

import com.devansh.indexing.model.CodeChunk;
import com.devansh.indexing.model.SourceFile;

@Component
public class EmbeddingContentBuilder {

    public String build(CodeChunk chunk) {

        SourceFile file = chunk.getSourceFile();

        return """
                File: %s
                Language: %s
                Package: %s
                Class: %s

                %s
                """.formatted(
                file.getFileName(),
                file.getLanguage(),
                file.getPackageName(),
                file.getClassName(),
                chunk.getContent()
        );

    }

}