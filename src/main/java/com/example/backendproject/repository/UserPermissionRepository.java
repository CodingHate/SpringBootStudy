package com.example.backendproject.repository;

import com.example.backendproject.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

// Q, 굳이 만들 필요가 있었는지?
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
}
