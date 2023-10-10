package com.example.backendproject.repository;

import com.example.backendproject.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
}
