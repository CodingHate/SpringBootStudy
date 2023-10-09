package com.example.backendproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // entity 변환 되는 사항을 감지 하기 위해서 설정한 어노테이션
public class BackEndProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndProjectApplication.class, args);
    }

}
