package com.devansh.indexing.dto.chroma;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCollectionRequest {

    private String name;

    private boolean get_or_create;

}