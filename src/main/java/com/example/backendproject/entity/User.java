package com.example.backendproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data // lombok
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String email;

//    @Column(nullable = true)
//    @CreatedDate
//    private String password;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createAt;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime modifiedAt;

}
