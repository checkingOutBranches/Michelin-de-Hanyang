package com.ssafy.michelin_de_hanyang.domain.auth.repository;

import com.ssafy.michelin_de_hanyang.domain.auth.entity.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findById(String id);
}
