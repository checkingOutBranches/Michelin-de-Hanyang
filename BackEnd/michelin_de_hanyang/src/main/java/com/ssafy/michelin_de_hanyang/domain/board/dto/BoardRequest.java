package com.ssafy.michelin_de_hanyang.domain.board.dto;

import com.ssafy.michelin_de_hanyang.domain.board.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardRequest {
    private String title;
    private String content;

    public Board toEntity(String userId) {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
