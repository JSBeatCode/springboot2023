package com.example.demo.config;

import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.cient.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.*;
//import org.springframework.http.client.*;
//import org.springframework.web.client.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    
    private int restConnectionTimeoutMs = 60000;
    
    private int connectionMaxIdleTime = 30000;
    
    private int connectionRequestTimeout = 30000;
    
    private int maxConnections = 500;
    
    private int maxConnectionsTotal = 5000;
    
    // @Bean(name="clientCredentialsRestTemplate")
    // public RestTemplate clientCredentialsRestTemplate() {
    //     return new RestTemplateBuilder().requestFactory(this::getRequestFactory)
    //         .additionalInterceptors(oauth2RequestInterceptor()).build();
    // }
    
    // @Bean
    // public OAuth2RequestInterceptor oauth2RequestInterceptor() {
    //     return new OAuth2RequestInterceptor();
    // }
    
    @Bean(name="restCaller")
    public RestTemplate getCommonRestCaller() {
        return new RestTemplateBuilder().requestFactory(this::getRequestFactory).build();
    }
    
    public ClientHttpRequestFactory getRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory 
            = new HttpComponentsClientHttpRequestFactory(httpClient());
        clientHttpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
        clientHttpRequestFactory.setReadTimeout(restConnectionTimeoutMs);
        clientHttpRequestFactory.setConnectTimeout(restConnectionTimeoutMs);
        return clientHttpRequestFactory;
    }
    
    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClientBuilder.create()
            .setMaxConnPerRoute(maxConnections)
            .setMaxConnTotal(maxConnectionsTotal)
            .evictIdleConnections(connectionMaxIdleTime, TimeUnit.MILLISECONDS)
            .build();
    }
    
}