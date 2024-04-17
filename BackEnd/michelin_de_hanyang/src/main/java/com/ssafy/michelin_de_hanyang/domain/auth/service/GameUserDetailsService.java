package com.ssafy.michelin_de_hanyang.domain.auth.service;

import org.springframework.security.core.userdetails.User;
import com.ssafy.michelin_de_hanyang.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        System.out.println("GameUserDetailsService.loadUserByUsername");
        return userRepository.findById(id)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Can't find user with this userId. -> " + id));
    }

    public UserDetails createUserDetails(com.ssafy.michelin_de_hanyang.domain.user.entity.User user) {
        return User.builder()
                .username(user.getId())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }
}