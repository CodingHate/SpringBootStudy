package com.example.backendproject.security;

import org.springframework.security.core.GrantedAuthority;

public class PermisionAuthority implements GrantedAuthority {

    // GrantedAuthority 이 인스턴스라서 생성해서 적용해야 한다.
    private final String permission;

    public PermisionAuthority(String permission)
    {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission;
    }
}
