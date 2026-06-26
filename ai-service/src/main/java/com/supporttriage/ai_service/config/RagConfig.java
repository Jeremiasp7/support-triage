package com.supporttriage.ai_service.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RagConfig {
    
    private static final Logger log = LoggerFactory.getLogger(RagConfig.class);

    private final VectorStore vectorStore;

    @PostConstruct
    public void loadKnowledgeBase() throws IOException {

        SearchRequest request = SearchRequest.builder()
                .query("teste")
                .topK(1)
                .similarityThreshold(0.0)
                .build();

        if (!vectorStore.similaritySearch(request).isEmpty()) {
            log.info("Base de conhecimento já indexada. Pulando indexação.");
            return;
        }

        log.info("Indexando base de conhecimento...");

        PathMatchingResourcePatternResolver resolver =
                new PathMatchingResourcePatternResolver();

        Resource[] resources =
                resolver.getResources("classpath:knowledge-base/*.md");

        List<Document> allDocuments = new ArrayList<>();

        TokenTextSplitter splitter = TokenTextSplitter.builder().build();

        for (Resource resource : resources) {
            TextReader reader = new TextReader(resource);
            allDocuments.addAll(splitter.apply(reader.get()));
        }

        vectorStore.add(allDocuments);

        log.info("{} chunks indexados.", allDocuments.size());
    }
}
