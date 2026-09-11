package com.devansh.repo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.devansh.repo.properties.GithubProperties;

@SpringBootApplication
@EnableConfigurationProperties(GithubProperties.class)
public class RepoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepoServiceApplication.class, args);
	}

}
