package com.ssafy.michelin_de_hanyang.domain.auth.service;

import com.ssafy.michelin_de_hanyang.domain.auth.dto.LoginRequest;
import com.ssafy.michelin_de_hanyang.domain.auth.dto.TokenDto;
import com.ssafy.michelin_de_hanyang.domain.auth.dto.UserDto;
import com.ssafy.michelin_de_hanyang.domain.auth.entity.BlackList;
import com.ssafy.michelin_de_hanyang.domain.auth.entity.RefreshToken;
import com.ssafy.michelin_de_hanyang.domain.auth.jwt.TokenProvider;
import com.ssafy.michelin_de_hanyang.domain.auth.repository.BlackListRepository;
import com.ssafy.michelin_de_hanyang.domain.auth.repository.TokenRepository;
import com.ssafy.michelin_de_hanyang.domain.user.entity.User;
import com.ssafy.michelin_de_hanyang.domain.user.repository.UserRepository;
import com.ssafy.michelin_de_hanyang.global.error.exception.InvalidParameterException;
import com.ssafy.michelin_de_hanyang.global.error.exception.ResourceNotFoundException;
import com.ssafy.michelin_de_hanyang.global.error.exception.TokenValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final LoginUserService loginUserService;
    private final TokenRepository tokenRepository;
    private final BlackListRepository blackListRepository;
    private final UserRepository userRepository;

    public TokenDto login(LoginRequest loginDto) {
        UserDto user = loginUserService.findUserById(loginDto.getId());
        if (user == null) {
            throw new ResourceNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDto.getId(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        log.info("auth >> {}" , String.valueOf(authentication));
        // JWT 토큰 생성

        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenDto tokenSet = tokenProvider.generateToken(authentication);
        // refreshToken 저장
        user.setRefreshToken(tokenSet.getRefreshToken());
        loginUserService.saveUser(user);
        RefreshToken refreshToken = RefreshToken.builder()
                .id(authentication.getName())
                .refreshToken(tokenSet.getRefreshToken())
                .build();

        tokenRepository.save(refreshToken);

        return tokenSet;

    }

    public void logout(String accessToken){
        String token = tokenProvider.resolveToken(accessToken);
        BlackList blackToken = new BlackList(token);
        String id = tokenProvider.extractId(accessToken);
        log.info("id = {}", id);
        blackListRepository.save(blackToken);
    }

    public TokenDto refresh(String accessToken, String refreshToken){
        String token = tokenProvider.resolveToken(accessToken);
        if (blackListRepository.findById(token).isPresent()){
            throw new TokenValidationException("로그아웃된 토큰입니다.");
        }
        Authentication authentication = tokenProvider.getAuthentication(token);
        log.info("authentication : {}", authentication);
        String principal = authentication.getName();
        log.info(principal);
        RefreshToken refreshTokenDB = tokenRepository.findById(principal).orElseThrow(() -> new ResourceNotFoundException("refreshToken 값이 존재하지 않습니다."));
        log.info("refreshTokenDB : {}", refreshTokenDB);
        if (!refreshTokenDB.getRefreshToken().equals(refreshToken)){
            throw new InvalidParameterException("토큰값이 일치하지 않습니다.");
        }
        if (!tokenProvider.validateToken(refreshToken)){
            throw new TokenValidationException("유효하지 않는 토큰입니다.");
        }

        User user = userRepository.findById(principal).orElseThrow(() -> new ResourceNotFoundException("해당 유저가 존재하지 않습니다."));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenDto tokenDto = tokenProvider.generateToken(authentication);
        user.setRefreshToken(tokenDto.getRefreshToken());
        userRepository.save(user);
        RefreshToken saveRefreshToken = RefreshToken.builder()
                .id(authentication.getName())
                .refreshToken(tokenDto.getRefreshToken())
                .build();

        tokenRepository.save(saveRefreshToken);
        return tokenDto;
    }
}
