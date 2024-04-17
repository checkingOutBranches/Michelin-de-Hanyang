package com.ssafy.michelin_de_hanyang.domain.auth.jwt;

import com.ssafy.michelin_de_hanyang.domain.auth.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private final Key key;

    public TokenProvider() {
        byte[] okey = Decoders.BASE64URL.decode(JwtProperties.SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(okey);
    }
    public TokenDto generateToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date accessTokenExpirationTime = new Date(now + 1000 * 60 * 30);
        // accessToken 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // 사용자 id
                .claim("role", authorities)
                .setExpiration(accessTokenExpirationTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        // refreshToken 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 1209600000L))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    // 토큰으로부터 정보 추출
    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException exception) {
            log.error("Invalid JWT Token", exception);
        } catch (ExpiredJwtException exception) {
            log.error("JWT is expired");
        } catch (IllegalArgumentException exception) {
            log.error("JWT is null or empty or only whitespace", exception);
        } catch (Exception exception) {
            log.error("JWT validation fails", exception);
        }
        return false;
    }

    public String resolveToken(String accessToken) {
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            return accessToken.substring(7);
        }
        throw new UnsupportedJwtException("지원하지 않는 토큰 형식입니다.");
    }

    public String extractId(String accessToken) {
        return parseClaims(resolveToken(accessToken)).getSubject();
    }

    public long getTokenExpiration(String accessToken) {
        String token = resolveToken(accessToken);
        Claims claims = parseClaims(token);
        long tokenExpirationTime = claims.getExpiration().getTime();
        long remainingTime = tokenExpirationTime - System.currentTimeMillis();
        return remainingTime > 0 ? remainingTime : 0;
    }
}