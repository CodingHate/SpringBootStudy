package com.example.backendproject.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserResponse {
    private String accessToekn;
    private String refreshToken;
}
