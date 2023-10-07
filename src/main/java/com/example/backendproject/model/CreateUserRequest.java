package com.example.backendproject.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String name;
    private Integer age;
    private String email;
}
