package com.devansh.repo.producer;

import com.devansh.repo.event.RepositoryImportedEvent;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RepositoryEventProducer {

    public static final String REPOSITORY_IMPORTED =
            "repository.imported";

    private final KafkaTemplate<
            String,
            RepositoryImportedEvent
            > kafkaTemplate;

    public RepositoryEventProducer(
            KafkaTemplate<
                    String,
                    RepositoryImportedEvent
                    > kafkaTemplate
    ) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishRepositoryImported(
            RepositoryImportedEvent event
    ) {

        kafkaTemplate.send(
                REPOSITORY_IMPORTED,
                event.getRepositoryId().toString(),
                event
        );
    }
}