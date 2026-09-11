package com.devansh.indexing.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.devansh.indexing.event.RepositoryImportedEvent;

import com.devansh.indexing.model.IndexingContext;
import com.devansh.indexing.model.Workspace;
import com.devansh.indexing.service.RepositoryIndexingService;
import com.devansh.indexing.service.RepositoryPreparationService;

@Component
public class RepositoryImportedConsumer {

    private final RepositoryPreparationService preparationService;
    private final RepositoryIndexingService indexingService;

    public RepositoryImportedConsumer(
            RepositoryPreparationService preparationService,
            RepositoryIndexingService indexingService) {

        this.preparationService = preparationService;
        this.indexingService = indexingService;
    }

    @KafkaListener(
            topics = "repository.imported",
            groupId = "anchor-indexing-worker"
    )
    public void consume(RepositoryImportedEvent event) {

        System.out.println(
                "Starting repository indexing: "
                        + event.getWorkspaceId()
        );

        try {

            UUID workspaceId =
                    UUID.fromString(event.getWorkspaceId());

            Workspace workspace =
                    preparationService.prepareRepository(
                            workspaceId,
                            event.getOwner(),
                            event.getRepositoryName(),
                            event.getDefaultBranch()
                    );

            IndexingContext context =
                    indexingService.indexRepository(
                            workspace
                    );

            System.out.println(
                    "Repository indexed successfully."
            );

            System.out.println(
                    "Workspace: "
                            + workspaceId
            );

            System.out.println(
                    "Files: "
                            + context.getDocuments().size()
            );

            System.out.println(
                    "Chunks: "
                            + context.getChunks().size()
            );

        } catch (Exception e) {

            System.err.println(
                    "Repository indexing failed: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}