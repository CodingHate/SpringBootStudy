package com.example.backendproject.repository;

import com.example.backendproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    Optional<User> getUsersByEmail(String email);

    List<User> getUsersByNameContains(String name);

    @Modifying
    @Query("UPDATE User u SET u.name=:newName WHERE u.id=:id")
    int updateName(Long id, String newName);

    int deleteByNameContains(String name);
}
