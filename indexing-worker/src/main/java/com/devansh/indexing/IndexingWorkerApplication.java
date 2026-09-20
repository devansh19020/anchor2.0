package com.devansh.indexing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.devansh.indexing.properties.ChromaProperties;
import com.devansh.indexing.properties.ChunkingProperties;
import com.devansh.indexing.properties.GeminiProperties;
import com.devansh.indexing.properties.GithubProperties;
import com.devansh.indexing.properties.WorkspaceProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		WorkspaceProperties.class,
		GithubProperties.class,
		ChunkingProperties.class,
		ChromaProperties.class,
		GeminiProperties.class
})
public class IndexingWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndexingWorkerApplication.class, args);
	}

}
