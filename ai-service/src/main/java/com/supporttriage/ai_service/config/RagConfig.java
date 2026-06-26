package com.supporttriage.ai_service.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
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
        log.info("Iniciando o carregamento da base de conhecimento. ");

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:knowledge-base/*.md");

        if (resources.length == 0) {
            log.warn("Nenhum documento encontrado");
            return;
        }

        List<Document> allDocuments = new ArrayList<>();
        TokenTextSplitter splitter = TokenTextSplitter.builder().build();

        for (Resource resource : resources) {
            log.info("Carregando documento: {}", resource.getFilename());
            TextReader reader = new TextReader(resource);
            List<Document> docs = reader.get();
            List<Document> chunks = splitter.apply(docs);
            allDocuments.addAll(chunks);
        }

        vectorStore.add(allDocuments);
        log.info("Base de conhecimento carregada: {} chunks indexados", allDocuments.size());
    }
}
