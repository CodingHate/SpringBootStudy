package com.example.backendproject.contoller;

import com.example.backendproject.entity.User;
import com.example.backendproject.model.LoginUserRequest;
import com.example.backendproject.repository.UserRepository;
import com.example.backendproject.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeApicontroller {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/get-token")
    public ResponseEntity<String> getToken(@RequestBody LoginUserRequest request)
    {
        if(request == null)
        {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> result = userRepository
                .getUsersByEmail(request.getEmail());

        if(result.isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        else {
            User user = result.get();
            if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(JwtUtils.createToken(request.getEmail()));
    }

}
