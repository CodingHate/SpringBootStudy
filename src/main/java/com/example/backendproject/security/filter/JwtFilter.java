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

        if(autorizationHeader == null)
        {
            filterChain.doFilter(request, response);
            return;
        }

        if(!autorizationHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;
        }

        String token = autorizationHeader.split(" ")[1];

        if(JwtUtils.isExpired(token))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String email = JwtUtils.getEmail(token);

        try
        {
            User user = userService.getUserByEmail(email);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                    (
                        user.getEmail(), null,
                        user.getPermissions().stream().map(permission -> new PermisionAuthority(permission.getPermission())).toList()
                    );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        catch (Exception ignored)
        {

        }
        filterChain.doFilter(request, response);
    }
}
