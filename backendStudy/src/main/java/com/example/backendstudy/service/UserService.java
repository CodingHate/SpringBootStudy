package com.example.backendstudy.service;

import com.example.backendstudy.entity.User;
import com.example.backendstudy.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 생성자를 자동으로 만들고, 자동으로 di로 넣어 준다.
public class UserService {
    private final UserRepository userRepository; //final

    public Optional<User> GetUserById(Long id){
        return userRepository.findById(id);
    }

    public List<User> GetUserListByNameKeyword(String keyword)
    {
        return userRepository.getUsersByNameContains(keyword);
    }

    // @Transactional(rollbackOn = Exception.class) 을 사용안한느 이유, save에 너엌ㅅ다.
    public User CreateUser(String name, Integer age){
        User newUser = new User();
        newUser.setAge(age);
        newUser.setName(name);
        return userRepository.save(newUser); // save 하는 순간 자동으로 date가 들어간다.
    }

    @Transactional(rollbackOn = Exception.class)
    public boolean UpdateUserNameById(Long id, String name){
        return userRepository.updateUserNameById(id, name) == 1;
    }

    @Transactional(rollbackOn = Exception.class)
    public void DeleteUserById(Long id){
        userRepository.deleteById(id); //repository에서 제공되는 함수이다.
    }
}
