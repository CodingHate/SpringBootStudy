package com.example.backendproject.contoller;

import com.example.backendproject.entity.User;
import com.example.backendproject.entity.UserPermission;
import com.example.backendproject.model.LoginUserRequest;
import com.example.backendproject.repository.UserRepository;
import com.example.backendproject.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController // 해당 클래스가 RESTful 웹 서비스의 컨트롤러임을 나타낸다. http요청과 응답을 수행한다.
@RequestMapping("/api/home")
@RequiredArgsConstructor  // 생성자를 자동으로 생성 해준다.
public class HomeApicontroller {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<List<String>> getToken(@RequestBody LoginUserRequest request){

        if(request == null){
            return ResponseEntity.badRequest().build();
        }

        Optional<User> result = userRepository.getUserByEmail(request.getEmail());

        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            User user = result.get();
            if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
                return ResponseEntity.notFound().build();
            }else{
                List<String> tokens = new ArrayList<>();
                tokens.add(JwtUtils.createAccessToken(user.getEmail(), user.getPermissions()));
                tokens.add(JwtUtils.createRefreshToken(user.getEmail(), user.getPermissions()));

                return ResponseEntity.ok(tokens);
            }
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> getToken(HttpServletRequest request, @RequestBody String accessToken){

        if(accessToken == null){
            return ResponseEntity.badRequest().build();
        }

        if(!JwtUtils.isExpired(accessToken)){
            return ResponseEntity.badRequest().build();
        }

        String headerValue  = request.getHeader("Authorization");

        if(headerValue == null){
            return ResponseEntity.status(401).build();
        }

        String refreshToken = headerValue.split(" ")[1];

        if(JwtUtils.isExpired(refreshToken)){
            return ResponseEntity.badRequest().build();
        }

        List<String> permissions = JwtUtils.getPermissions(refreshToken);
        List<UserPermission> userPermissions = new ArrayList<>();
        for(String permission : permissions){
            UserPermission userPermission = new UserPermission();
            userPermission.setPermission(permission);
            userPermissions.add(userPermission);
        }

        return ResponseEntity
                .ok(JwtUtils.createAccessToken(JwtUtils.getEmail(refreshToken), userPermissions));
    }
}
