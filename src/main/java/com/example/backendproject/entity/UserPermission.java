package com.example.backendproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name ="user_permissions")
@EntityListeners(AuditingEntityListener.class) // 바뀌는 사항에 대해 자동으로 적용
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String permission;

    @ManyToOne(fetch = FetchType.EAGER) // user는 여러개의 permission을 가질 수 있다.
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createAt;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
