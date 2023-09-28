package com.example.backendstudy.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


// const: complie할 때 , final: 생성할 때 주입이 된다.
@Data
@Entity
@Table(name = "users") // table 명, [h2 data base에서 User가 미리 지정되어있어 다른것으로 예약어]
@EntityListeners(AuditingEntityListener.class)  // 자동으로 넣어주는 어노테이션
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1식 증가한다. identity
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = true)
    private Integer age;

    @Column
    @CreatedDate
    private LocalDateTime createDate; // 스프링에서 자동으로 넣어준다. LocalDateTime이 제일 좋다

    @Column
    @LastModifiedDate
    private LocalDateTime modifyDate;  // update date
}
