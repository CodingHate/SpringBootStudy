package com.example.backendproject.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JwtUtils {

    private static final int EXPRIRE_TIME = 1000 *60 * 60; // 시간

    private static final String key = "5958bc44-5463-441c-8a92-862b10f73828-b5bf40ad-ef91-44ee-93a1-a5201371bdcd" +
            "a3bfdfac-ba59-422a-93a1-84fb8b1e7b31-81f0cdae-1786-46f4-bba5-92010f7d2a14";

    private static String EMAIL_KEY = "email";

    // JWT TOKEN 발급
    public static String createToken(String email)
    {
        Claims claims = Jwts.claims();
        claims.put(EMAIL_KEY,email);

        Date now = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+EXPRIRE_TIME))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();

    }

    public static String getEmail(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(EMAIL_KEY, String.class);
    }

    public static boolean isExpired(String token)
    {
        Date expireDate = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expireDate.before(new Date(System.currentTimeMillis()));
    }
}
