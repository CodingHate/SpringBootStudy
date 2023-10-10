package com.example.backendproject.security.filter;

import com.example.backendproject.entity.User;
import com.example.backendproject.security.PermisionAuthority;
import com.example.backendproject.security.jwt.JwtUtils;
import com.example.backendproject.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String autorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //Header의 Authorization 값이 비어있으면 -> Jwt Token을 전송하지 않음 -> 로그인 하지 않음
        if(autorizationHeader == null)
        {
            filterChain.doFilter(request, response);
            return;
        }

        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 -> 잘못된 토큰
        if(!autorizationHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;
        }

        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
        String token = autorizationHeader.split(" ")[1];

        // 전송 받은 Jwt Token이 만료되었으면 -> 다음 필터 진행 (인증 x)
        if(JwtUtils.isExpired(token))
        {
            filterChain.doFilter(request, response);
            return;
        }

        // Jwt에서 email 추출
        String email = JwtUtils.getEmail(token);

        try
        {
            // 추출한 loginId로 User 찾아오기
            User user = userService.getUserByEmail(email);

            // email 정보로 UsernamePasswordAuthenticationToken 발급
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                    (
                        user.getEmail(), null,
                        user.getPermissions().stream().map(permission -> new PermisionAuthority(permission.getPermission())).toList()
                    );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 권한 부여 / USernamePAsswordAuthenticationFilter에 전달
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // USernamePasswordAuthenticationFilter는 위 권한 토큰이 있는지 확인한다.
        }
        catch (Exception ignored)
        {

        }
        filterChain.doFilter(request, response);
    }
}
