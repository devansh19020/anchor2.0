package com.devansh.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.devansh.ai.properties.ChromaProperties;
import com.devansh.ai.properties.GeminiProperties;
import com.devansh.ai.properties.SearchProperties;

@SpringBootApplication
@EnableConfigurationProperties({GeminiProperties.class, ChromaProperties.class, SearchProperties.class})
public class AiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiServiceApplication.class, args);
	}
}
