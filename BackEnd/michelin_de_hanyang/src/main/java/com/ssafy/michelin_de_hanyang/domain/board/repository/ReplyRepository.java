package com.ssafy.michelin_de_hanyang.domain.board.repository;

import com.ssafy.michelin_de_hanyang.domain.board.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReplyRepository extends MongoRepository<Reply, String> {
}
