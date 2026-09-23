package com.devansh.ai.prompt;

import com.devansh.ai.model.RetrievedChunk;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptBuilder {

    public String build(
            String question,
            List<RetrievedChunk> chunks
    ) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
You are an expert Senior Java Software Architect.

You are helping a developer understand a software project.

==========================
RULES
==========================

1. Answer ONLY using the provided repository context.
2. Never invent classes, methods or APIs.
3. If the context is insufficient, explicitly say so.
4. Mention filenames whenever possible.
5. Explain relationships between classes if relevant.
6. Prefer concise but technically accurate explanations.

==========================
REPOSITORY INFORMATION
==========================

Repository:
Anchor

Primary Language:
Java

Framework:
Spring Boot

==========================
REPOSITORY CONTEXT
==========================

""");

        int sourceNumber = 1;

        for (RetrievedChunk chunk : chunks) {

            prompt.append("==========================\n");
            prompt.append("SOURCE ").append(sourceNumber++);
            prompt.append("\n==========================\n\n");

            prompt.append("File: ")
                    .append(chunk.getFileName())
                    .append("\n");

            if (chunk.getPackageName() != null) {
                prompt.append("Package: ")
                        .append(chunk.getPackageName())
                        .append("\n");
            }

            if (chunk.getClassName() != null) {
                prompt.append("Class: ")
                        .append(chunk.getClassName())
                        .append("\n");
            }

            prompt.append("\n");

            String language = switch (chunk.getLanguage()) {

                case "JAVA" -> "java";
                case "KOTLIN" -> "kotlin";
                case "XML" -> "xml";
                case "YAML" -> "yaml";
                case "MARKDOWN" -> "markdown";
                default -> "";

            };

            prompt.append("```")
                    .append(language)
                    .append("\n");

            prompt.append(chunk.getContent());

            prompt.append("\n```\n\n");

        }

        prompt.append("""
==========================
QUESTION
==========================

""");

        prompt.append(question);

        prompt.append("""



==========================
ANSWER
==========================

""");

        return prompt.toString();

    }

}