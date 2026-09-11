package com.devansh.indexing.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SourceDocument {

    private SourceFile sourceFile;

    private String content;

}