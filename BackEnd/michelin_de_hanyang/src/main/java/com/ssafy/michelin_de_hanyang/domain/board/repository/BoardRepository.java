package com.ssafy.michelin_de_hanyang.domain.board.repository;

import com.ssafy.michelin_de_hanyang.domain.board.Board;
import com.ssafy.michelin_de_hanyang.domain.board.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends MongoRepository<Board, String> {
    List<Reply> findByIdIn(List<String> ids);
}
