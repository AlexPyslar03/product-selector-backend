package com.alexpyslar03.productselectorbackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ProductSelectorBackendApplication {

    public static void main(String[] args) {
        log.info("Запуск ProductSelectorBackendApplication...");
        SpringApplication app = new SpringApplication(ProductSelectorBackendApplication.class);
        app.setAddCommandLineProperties(true);  // Включает поддержку командных аргументов
        app.setBannerMode(Banner.Mode.OFF);  // Отключает баннер Spring Boot при запуске
        app.run(args);
        log.info("ProductSelectorBackendApplication успешно запущен.");
    }
}
