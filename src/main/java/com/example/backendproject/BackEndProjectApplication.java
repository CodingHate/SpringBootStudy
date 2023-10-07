package com.example.backendproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackEndProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndProjectApplication.class, args);
    }

}
