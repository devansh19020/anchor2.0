package com.devansh.indexing.util;

import java.util.List;

public final class ChromaConstants {

    private ChromaConstants() {
    }

    public static final String DEFAULT_TENANT = "default_tenant";

    public static final String DEFAULT_DATABASE = "default_database";

    public static final List<String> QUERY_INCLUDE =
        List.of(
                "documents",
                "metadatas",
                "distances"
        );

    public static final int DEFAULT_TOP_K = 5;

}