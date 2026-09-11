package com.devansh.indexing.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IndexingContext {

    private Workspace workspace;

    @Builder.Default
    private List<SourceDocument> documents = new ArrayList<>();

    @Builder.Default
    private List<CodeChunk> chunks = new ArrayList<>();

}