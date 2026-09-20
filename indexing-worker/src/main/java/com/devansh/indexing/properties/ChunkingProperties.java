package com.devansh.indexing.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "chunking")
public class ChunkingProperties {

    private int chunkSize;

    private int overlap;

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public int getOverlap() {
        return overlap;
    }

    public void setOverlap(int overlap) {
        this.overlap = overlap;
    }
}