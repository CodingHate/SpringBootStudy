package com.example.backendstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing     // DB에 자동으로 넣는 field를 수행 해주는 어노테이션
@SpringBootApplication //자기 시스템을 서치하여 등록을 한다.
public class BackendStudyApplication {
    // main 단이다.
    public static void main(String[] args) {
        SpringApplication.run(BackendStudyApplication.class, args);
    }

}
