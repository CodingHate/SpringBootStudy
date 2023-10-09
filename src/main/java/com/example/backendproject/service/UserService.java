package com.example.backendproject.service;

import com.example.backendproject.entity.User;
import com.example.backendproject.entity.UserPermission;
import com.example.backendproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(String name, Integer age, String email, String encodePassword)
    {
        // email 존재 확인
        boolean exist = userRepository.existsByEmail(email);

        if(!exist)
        {
            User user = new User();
            user.setName(name);
            user.setAge(age);
            user.setEmail(email);
            user.setPassword(encodePassword);

            UserPermission userPermission = new UserPermission();
            userPermission.setPermission("USER");
            user.getPermissions().add(userPermission);

            return userRepository.save(user); // save는 jpa에서 제공 해주는 함수이다.
        }
        else
        {
            // 동일 메일이 있으면 중복으로 에러 발생
            throw  new RuntimeException("email already exist");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public User getUserByEmail(String email)
    {
        return userRepository.getUsersByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional(rollbackOn = Exception.class)
    public boolean updateUser(Long id, String newName)
    {
        // 직접 만든 쿼리 적용
        return userRepository.updateName(id, newName) == 1;
    }

    @Transactional(rollbackOn = Exception.class)
    public boolean deleteUser(String name)
    {
        // 삭제 된 내용이 1개 이상인 경우 true
        return userRepository.deleteByNameContains(name) > 0;
    }

}
