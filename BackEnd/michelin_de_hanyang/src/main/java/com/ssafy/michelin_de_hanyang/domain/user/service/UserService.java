package com.ssafy.michelin_de_hanyang.domain.user.service;

import com.ssafy.michelin_de_hanyang.domain.user.dto.RankingResponse;
import com.ssafy.michelin_de_hanyang.domain.user.dto.SaveRequest;
import com.ssafy.michelin_de_hanyang.domain.user.entity.User;
import com.ssafy.michelin_de_hanyang.domain.user.repository.UserRepository;
import com.ssafy.michelin_de_hanyang.global.error.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("bae1997@naver.com");
        mailSender.send(message);
    }

//    public String regist(UserDto userDto) {
//        User user = new User();
//        user.setEmail(userDto.getEmail());
//        user.setUsername(userDto.getUsername());
//        userRepository.save(user);
//        return user.getId();
//    }

//    private String getCurrentId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new AuthenticationFailedException("사용자가 인증되지 않았습니다.");
//        }
//        return ((User) authentication.getPrincipal()).getId();
//    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<Integer> findUserLevelById(String id) {
        return userRepository.findById(id)
                .map(User::getLv);
    }

    public User saveGameData(String userId, SaveRequest saveRequest) {
        log.info("============================== saveRequest data");
        log.info(String.valueOf(saveRequest.getExp()));
        log.info("============================== saveRequest isEmpty");
        log.info(String.valueOf(saveRequest.getInventory().isEmpty()));
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        log.info("============================== userId");
        log.info(existingUser.getId());
        User updatedUser = saveRequest.toEntity(existingUser);
        log.info("============================== updateUser");
        log.info(updatedUser.getId());
        return userRepository.save(updatedUser);
    }

    public List<RankingResponse> findUsersByQuestSuccess(int questSuccess) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("questSuccess").is(questSuccess)),
                sort(Sort.Direction.DESC, "lv", "username", "_id"),
                project("questSuccess", "lv", "username", "exp").andInclude("id")
        );

        AggregationResults<RankingResponse> results = mongoTemplate.aggregate(aggregation, "users", RankingResponse.class);
        return results.getMappedResults();
    }


}
