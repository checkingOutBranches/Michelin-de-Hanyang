package com.ssafy.michelin_de_hanyang.domain.auth.repository;

import com.ssafy.michelin_de_hanyang.domain.auth.entity.BlackList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BlackListRepository extends MongoRepository<BlackList, String> {
    Optional<BlackList> findById(String accessToken);
}
