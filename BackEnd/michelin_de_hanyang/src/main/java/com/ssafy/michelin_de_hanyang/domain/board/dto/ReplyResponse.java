package com.ssafy.michelin_de_hanyang.domain.board.dto;

import com.ssafy.michelin_de_hanyang.domain.board.Reply;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReplyResponse {
    private String id;
    private String content;
    private LocalDateTime createdAt;
    private String username;

    public ReplyResponse(Reply reply, String username) {
        this.id = reply.getId();
        this.content = reply.getReply();
        this.createdAt = reply.getCreatedAt();
        this.username = username;
    }
}

