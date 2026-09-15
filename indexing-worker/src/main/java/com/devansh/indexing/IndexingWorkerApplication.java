package com.devansh.indexing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.devansh.indexing.properties.GithubProperties;
import com.devansh.indexing.properties.WorkspaceProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		WorkspaceProperties.class,
		GithubProperties.class
})
public class IndexingWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndexingWorkerApplication.class, args);
	}

}
