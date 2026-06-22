package com.supporttriage.ai_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.web.client.RestClient;

@Configuration
public class GraphQlClientConfig {
    
    @Value("{ai-service.graphql-url}")
    private String aiServiceUrl;

    @Bean
    public HttpSyncGraphQlClient aiServiceGraphQlClient() {
        RestClient restClient = RestClient.builder()
            .baseUrl(aiServiceUrl)
            .build();
        
            return HttpSyncGraphQlClient.builder(restClient).build();
    }
}
