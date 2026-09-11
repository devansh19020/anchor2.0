package com.devansh.indexing.dto.chroma;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryResponse {

    private List<List<String>> ids;

    private List<List<String>> documents;

    private List<List<Map<String, Object>>> metadatas;

    private List<List<Double>> distances;

}