package com.kisansathi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.kisansathi.repository")
public class KisanSaathiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KisanSaathiApplication.class, args);
    }

    /**
     * WebClient.Builder का Bean डिफाइन करना ज़रूरी है ताकि 
     * ClaudeVisionService इसे ऑटो-इंजेक्ट (Autowire) कर सके।
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}