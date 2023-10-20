package com.example.backendproject.contoller;

import com.example.backendproject.entity.User;
import com.example.backendproject.entity.UserPermission;
import com.example.backendproject.model.LoginUserRequest;
import com.example.backendproject.model.LoginUserResponse;
import com.example.backendproject.repository.UserRepository;
import com.example.backendproject.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
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
@RequestMapping("/api/auth")
@RequiredArgsConstructor  // 생성자를 자동으로 생성 해준다.
public class AuthApiController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(HttpServletRequest request){

        if(request == null){
            return ResponseEntity.badRequest().build();
        }

        String headerValue = request.getHeader("Authorization");

        if(headerValue == null){
            return  ResponseEntity.badRequest().build();
        }
        else if(!headerValue.startsWith("Basic " )){
            return ResponseEntity.badRequest().build();
        }

        String userNamePasswordEncryptValue = headerValue.replace("Basic ", "");
        byte[] userNamePasswordbyteValue = Base64.decodeBase64(userNamePasswordEncryptValue);
        String userNamePasswordValue = new String(userNamePasswordbyteValue);
        if(!userNamePasswordValue.contains(":")){
            ResponseEntity.badRequest().build();
        }

        String[] userNameAndPassword = userNamePasswordValue.split(":");
        if(userNameAndPassword.length !=2 ){
            ResponseEntity.badRequest().build();
        }

        String email = userNameAndPassword[0];
        String password = userNameAndPassword[1];

        Optional<User> result = userRepository.getUserByEmail(email);

        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            User user = result.get();
            if(!passwordEncoder.matches(password, user.getPassword())){
                return ResponseEntity.notFound().build();
            }else{
                String accessToken = JwtUtils.createAccessToken(user.getEmail(), user.getPermissions());
                String refreshToken = JwtUtils.createRefreshToken(user.getEmail(), user.getPermissions());
                LoginUserResponse response = new LoginUserResponse();
                response.setAccessToekn(accessToken);
                response.setRefreshToken(refreshToken);

                return ResponseEntity.ok(response);
            }
        }
    }

    @PostMapping("/get-new-access-token")
    public ResponseEntity<String> getToken(HttpServletRequest request){

        String headerValue = request.getHeader("Authorization");

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
