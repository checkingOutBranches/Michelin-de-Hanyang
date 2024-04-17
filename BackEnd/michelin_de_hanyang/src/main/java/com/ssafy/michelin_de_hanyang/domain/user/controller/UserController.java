package com.ssafy.michelin_de_hanyang.domain.user.controller;

import com.ssafy.michelin_de_hanyang.domain.auth.jwt.TokenProvider;
import com.ssafy.michelin_de_hanyang.domain.user.dto.InquiryRequest;
import com.ssafy.michelin_de_hanyang.domain.user.dto.RankingResponse;
import com.ssafy.michelin_de_hanyang.domain.user.dto.SaveRequest;
import com.ssafy.michelin_de_hanyang.domain.user.entity.User;
import com.ssafy.michelin_de_hanyang.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User", description = "유저 관련 API를 명세합니다.")
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

//    @Operation(summary = "회원 등록 기능", description = "회원을 등록합니다.")
//    @PostMapping
//    public ResponseEntity<String> regist(@RequestBody UserDto userDto) {
//
//        String result = userService.regist(userDto);
//
//        return new ResponseEntity<>(result, HttpStatus.CREATED);
//    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/level")
    public ResponseEntity<Integer> getUserLevelById(@PathVariable String id) {
        Optional<Integer> level = userService.findUserLevelById(id);
        return level.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/questSuccess/{questSuccess}")
    public ResponseEntity<List<RankingResponse>> getUsersByQuestSuccess(@PathVariable int questSuccess) {
        List<RankingResponse> rankingResponses = userService.findUsersByQuestSuccess(questSuccess);
        if(rankingResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rankingResponses);
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveGameData(HttpServletRequest httprequest, @RequestBody SaveRequest saveRequest) {
        String token = httprequest.getHeader(HttpHeaders.AUTHORIZATION);
        String userId = tokenProvider.extractId(token);
        log.info("============================== userId");
        log.info(userId);
        User savedUser = userService.saveGameData(userId, saveRequest);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/inquiry")
    public ResponseEntity<?> receiveInquiry(@RequestBody InquiryRequest inquiryRequest) {
        String subject = "고객문의: " + inquiryRequest.getName() + " (" + inquiryRequest.getEmail() + ")";
        String text = "문의 내용: \n" + inquiryRequest.getContent();
        userService.sendMail("bae1997@naver.com", subject, text);
        return ResponseEntity.ok("문의가 성공적으로 전송되었습니다.");
    }
}
