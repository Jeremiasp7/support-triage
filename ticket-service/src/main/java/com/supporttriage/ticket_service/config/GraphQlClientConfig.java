package com.supporttriage.ticket_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.web.client.RestClient;

@Configuration
public class GraphQlClientConfig {
    
    @Value("${ai-service.graphql-url}")
    private String aiServiceGraphQlUrl;

    @Bean
    public HttpSyncGraphQlClient aiServiceGraphQlClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(aiServiceGraphQlUrl)
                .build();

        return HttpSyncGraphQlClient.builder(restClient).build();
    }
}
