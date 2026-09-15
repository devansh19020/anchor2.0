package com.devansh.indexing.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.devansh.indexing.event.RepositoryImportedEvent;
import com.devansh.indexing.service.RepositoryIndexingService;

@Component
public class RepositoryImportConsumer {

    private final RepositoryIndexingService indexingService;

    public RepositoryImportConsumer(
            RepositoryIndexingService indexingService
    ) {
        this.indexingService = indexingService;
    }

    @KafkaListener(
            topics = "repository.imported",
            groupId = "anchor-indexing-worker"
    )
    public void consume(
            RepositoryImportedEvent event
    ) {

        System.out.println(
                "Received repository import event: "
                        + event.getRepositoryUrl()
        );

        indexingService.indexRepository(event);
    }
}