package com.alexpyslar03.productselectorbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Основное количество потоков
        executor.setMaxPoolSize(10); // Максимальное количество потоков
        executor.setQueueCapacity(100); // Вместимость очереди
        executor.setThreadNamePrefix("AsyncThread-"); // Префикс имен потоков
        executor.initialize(); // Инициализация Executor
        return executor;
    }
}