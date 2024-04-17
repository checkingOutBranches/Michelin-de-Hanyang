package com.ssafy.michelin_de_hanyang.domain.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER);
        if (StringUtils.hasText(token) && token.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return token.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.debug("uri = {}", request.getRequestURI());

        if (request.getRequestURI().startsWith("/api/users/questSuccess/")) {
            chain.doFilter(request, response);
            return;
        }
        // Request Header에서 JWT Token 추출
        String token = resolveToken(request);
        log.debug("access token in jwtFilter = {}", token);
        // validateToken으로 토큰 유효성 검사
        if (tokenProvider.validateToken(token) && token != null) {
            log.debug("Token is valid");
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
            Authentication authentication = tokenProvider.getAuthentication(token);
            log.debug("Authentication object after parsing token: {}", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.debug("Token is invalid or null");
        }
        chain.doFilter(request, response);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            log.debug("Request 처리 후 SecurityContext 인증 정보: '{}'", authentication.getName());
        } else {
            log.debug("Request 처리 후 SecurityContext에 인증 정보가 없습니다.");
        }
    }
}