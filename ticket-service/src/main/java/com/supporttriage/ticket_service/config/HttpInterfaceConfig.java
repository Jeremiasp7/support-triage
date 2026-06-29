package com.supporttriage.ticket_service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.supporttriage.ticket_service.client.AiServiceClient;
import com.supporttriage.ticket_service.client.NotificationServiceClient;

@Configuration
public class HttpInterfaceConfig {

    @Bean
    @Primary
    public RestClient.Builder defaultRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean("loadBalancedBuilder")
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public AiServiceClient aiServiceClient(@Qualifier("loadBalancedBuilder") RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl("http://ai-service").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(AiServiceClient.class);
    }

    @Bean
    public NotificationServiceClient notificationServiceClient(@Qualifier("loadBalancedBuilder") RestClient.Builder builder) {
        // Aponta para o nome do serviço no Eureka!
        RestClient restClient = builder.baseUrl("http://notification-service").build(); 
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(NotificationServiceClient.class);
    }
}