package com.ssafy.michelin_de_hanyang.domain.board.dto;

import com.ssafy.michelin_de_hanyang.domain.board.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardsResponse {
    private final String id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final String username;
    private final List<ReplyResponse> replies;

    public BoardsResponse(Board board, String username, List<ReplyResponse> replies) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.username = username;
        this.replies = replies;
    }
}

