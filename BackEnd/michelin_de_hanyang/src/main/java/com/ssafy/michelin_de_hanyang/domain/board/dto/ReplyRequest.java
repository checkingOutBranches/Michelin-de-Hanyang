package com.ssafy.michelin_de_hanyang.domain.board.dto;

import com.ssafy.michelin_de_hanyang.domain.board.Reply;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReplyRequest {
    private String reply;

    public Reply toEntity(String boardId, String userId) {
        return Reply.builder()
                .boardId(boardId)
                .userId(userId)
                .reply(this.reply)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
