package com.ced.costefficientdeplyment.service.impl;

import com.ced.costefficientdeplyment.service.DataLoadingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DataLoadingServiceImpl implements DataLoadingService {

    private static final Logger logger = LoggerFactory.getLogger(DataLoadingServiceImpl.class);
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    @Value("file:C:/Users/user/Desktop/cost-efficient-deplyment/pipelines.json")
    private Resource resource;

    private final VectorStore vectorStore;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService;

    public DataLoadingServiceImpl(VectorStore vectorStore, ObjectMapper objectMapper, ExecutorService executorService) {
        Assert.notNull(vectorStore, "VectorStore must not be null.");
        this.vectorStore = vectorStore;
        this.objectMapper = objectMapper;
        this.executorService = executorService;
    }

    @Override
    public void load() {
        try {
            logger.info("Starting multi-threaded JSON loading from: {}", resource.getFilename());

            // Validate resource
            if (!resource.exists() || !resource.isReadable()) {
                throw new IllegalStateException("Resource not accessible: " + resource.getDescription());
            }

            // 1. Parse JSON
            List<Map<String, Object>> jsonItems = objectMapper.readValue(
                    resource.getInputStream(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class)
            );
            logger.info("Parsed {} JSON items", jsonItems.size());

            // 2. Split items into chunks for parallel processing
            int chunkSize = Math.max(1, jsonItems.size() / THREAD_POOL_SIZE);
            List<List<Map<String, Object>>> chunks = new ArrayList<>();
            for (int i = 0; i < jsonItems.size(); i += chunkSize) {
                int end = Math.min(i + chunkSize, jsonItems.size());
                chunks.add(jsonItems.subList(i, end));
            }
            logger.debug("Split into {} chunks", chunks.size());

            // 3. Process chunks in parallel
            List<CompletableFuture<List<Document>>> futures = new ArrayList<>();
            TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();

            for (List<Map<String, Object>> chunk : chunks) {
                CompletableFuture<List<Document>> future = CompletableFuture.supplyAsync(() -> {
                    List<Document> documents = new ArrayList<>();
                    for (Map<String, Object> item : chunk) {
                        try {
                            String content = objectMapper.writeValueAsString(item);
                            Document doc = new Document(content);
                            doc.getMetadata().put("filename", resource.getFilename());
                            doc.getMetadata().put("version", 1);
                            documents.add(doc);
                        } catch (Exception e) {
                            logger.error("Error processing item: {}", e.getMessage());
                            throw new RuntimeException(e);
                        }
                    }
                    return tokenTextSplitter.split(documents);
                }, executorService);
                futures.add(future);
            }

            // 4. Collect results
            List<Document> allSplitDocuments = new ArrayList<>();
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> futures.stream()
                            .map(CompletableFuture::join)
                            .flatMap(List::stream)
                            .collect(Collectors.toList()))
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            logger.error("Error in parallel processing: {}", ex.getMessage());
                        } else {
                            allSplitDocuments.addAll(result);
                            logger.info("Collected {} split documents", allSplitDocuments.size());
                        }
                    }).join();

            if (allSplitDocuments.isEmpty()) {
                throw new RuntimeException("No documents processed successfully");
            }

            // 5. Check if data exists
            String searchQuery = "filename:" + resource.getFilename() + " version:" + 1;
            List<Document> existingDocs = vectorStore.similaritySearch(searchQuery);
            if (!existingDocs.isEmpty()) {
                logger.info("Data for '{}' version {} already loaded. Exiting.", resource.getFilename(), 1);
                return;
            }

            // 6. Store in vector store
            vectorStore.write(allSplitDocuments);
            logger.info("Stored {} documents in vector store", allSplitDocuments.size());

        } catch (Exception e) {
            logger.error("Failed to load JSON data: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to load JSON data into vector store", e);
        } finally {
            // Clean up executor service (optional, depending on lifecycle)
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }

    }
}
