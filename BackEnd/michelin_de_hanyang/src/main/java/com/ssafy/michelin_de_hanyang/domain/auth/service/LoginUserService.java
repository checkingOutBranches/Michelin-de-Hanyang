package com.ssafy.michelin_de_hanyang.domain.auth.service;

import com.ssafy.michelin_de_hanyang.domain.auth.dto.UserDto;
import com.ssafy.michelin_de_hanyang.domain.auth.dto.UserSign;
import com.ssafy.michelin_de_hanyang.domain.auth.jwt.TokenProvider;
import com.ssafy.michelin_de_hanyang.domain.user.dto.Role;
import com.ssafy.michelin_de_hanyang.domain.user.entity.User;
import com.ssafy.michelin_de_hanyang.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginUserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    public boolean signUp(UserSign userSign){
        if (userRepository.findById(userSign.getId()).isPresent()){
            // 해당 id가 DB에 저장되어있다면
            return true;
        }
        User user = User.builder()
                .id(userSign.getId())
                .username(userSign.getName())
                .password(passwordEncoder.encode(userSign.getPassword()))
                .role(Role.USER)
                .lv(1)
                .exp(0)
                .money(0)
                .hp(500)
                .currentArm(0)
                .currentVehicle(0)
                .time(0.0)
                .learnedList(new ArrayList<>(Arrays.asList("rp001", "rp002", "rp003")))
                .todayMenus(new ArrayList<>())
                .workers(0)
                .onDutyWorkers(0)
                .soldFood(new ArrayList<>())
                .noSound(false)
                .questSuccess(0)
                .lastScene("Main")
                .inventory(new ArrayList<>())
                .lastXy(new ArrayList<>(Arrays.asList(0.0, 0.0)))
                .currentField("Field 1")
                .build();
        userRepository.save(user);
        return false;
    }

    public UserDto findUserById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id 값은 null이 될 수 없습니다.");
        }
//        User user = userRepository.findById(id).orElseThrow();
//        return UserDto.getUser(user);
        return userRepository.findById(id)
                .map(UserDto::getUser)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));
    }

    public void saveUser(UserDto userDto) {
        User user = User.getUser(userDto);
        userRepository.save(user);
    }

    public boolean idDuplicated(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.isEmpty();
    }

    public UserDto getInfo(String accessToken){
        String userId = tokenProvider.extractId(accessToken);
        log.info("userId : {}", userId);
        return findUserById(userId);
    }
}
