package com.alexpyslar03.productselectorbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Конфигурация асинхронных операций.
 * <p>
 * Этот класс настраивает пул потоков для обработки асинхронных задач
 * с использованием Spring. Пул потоков позволяет выполнять методы,
 * помеченные аннотацией @Async, в фоновом режиме.
 * </p>
 */
@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {

    /**
     * Настраивает пул потоков для выполнения асинхронных задач.
     *
     * @return настроенный экземпляр Executor
     */
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