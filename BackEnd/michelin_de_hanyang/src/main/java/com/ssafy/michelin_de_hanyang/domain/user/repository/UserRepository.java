package com.ssafy.michelin_de_hanyang.domain.user.repository;

import com.ssafy.michelin_de_hanyang.domain.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
//    User findByEmailAndProvider(String email, String registrationId);

//    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);
}

