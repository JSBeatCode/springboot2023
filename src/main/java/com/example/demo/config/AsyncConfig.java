package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
@EnableAsync는 클래스 단에, Async는 다른 클래스의 메소드 단에 사용하면 SPring Boot 비동기 메서드를 구현함. 이로인해 응답성과 성능 향상
*/

@Configuration
@EnableAsync
public class AsyncConfig {
    
    @Bean(name="threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(30);
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setThreadNamePrefix("ASYNC_THREAD-");
        taskExecutor.initialize();
        
        return taskExecutor;
    }
}
