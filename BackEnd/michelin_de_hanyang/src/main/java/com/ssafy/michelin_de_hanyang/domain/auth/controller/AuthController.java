package com.ssafy.michelin_de_hanyang.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.michelin_de_hanyang.domain.auth.dto.LoginRequest;
import com.ssafy.michelin_de_hanyang.domain.auth.dto.TokenDto;
import com.ssafy.michelin_de_hanyang.domain.auth.dto.UserDto;
import com.ssafy.michelin_de_hanyang.domain.auth.dto.UserSign;
import com.ssafy.michelin_de_hanyang.domain.auth.jwt.JwtProperties;
import com.ssafy.michelin_de_hanyang.domain.auth.service.AuthService;
import com.ssafy.michelin_de_hanyang.domain.auth.service.LoginUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://j10a609.p.ssafy.io", "http://localhost:3000"}, exposedHeaders = "*")
@RequestMapping("/api/auth")
@Log4j2
public class AuthController {

    private final LoginUserService loginUserService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSign userSign){
        if (loginUserService.signUp(userSign)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        TokenDto tokenDto = authService.login(loginRequest);
        UserDto user = loginUserService.findUserById(loginRequest.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("token", tokenDto);
        map.put("data", user);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @PostMapping("/check/{id}")
    public ResponseEntity<?> checkId(@PathVariable String id){

        if (loginUserService.idDuplicated(id)){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    @GetMapping("/testSecurityContext")
    public ResponseEntity<?> testSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            log.info("No authentication in SecurityContext");
            return ResponseEntity.ok("No authentication in SecurityContext");
        } else {
            log.info("Authentication object from SecurityContext: {}", authentication);
            UserDto currentUser = (UserDto) authentication.getPrincipal();
            return ResponseEntity.ok("Authenticated user: " + currentUser.getUsername());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken){
        authService.logout(accessToken);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request){
        String access = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refresh = request.getHeader(JwtProperties.REFRESH_TOKEN);
        log.info(access);
        TokenDto token = authService.refresh(access, refresh);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
