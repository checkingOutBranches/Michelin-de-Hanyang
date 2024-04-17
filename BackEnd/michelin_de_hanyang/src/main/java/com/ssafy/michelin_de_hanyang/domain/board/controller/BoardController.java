package com.ssafy.michelin_de_hanyang.domain.board.controller;

import com.ssafy.michelin_de_hanyang.domain.auth.jwt.TokenProvider;
import com.ssafy.michelin_de_hanyang.domain.board.Board;
import com.ssafy.michelin_de_hanyang.domain.board.Reply;
import com.ssafy.michelin_de_hanyang.domain.board.dto.BoardRequest;
import com.ssafy.michelin_de_hanyang.domain.board.dto.BoardsResponse;
import com.ssafy.michelin_de_hanyang.domain.board.dto.ReplyRequest;
import com.ssafy.michelin_de_hanyang.domain.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/board")
@CrossOrigin(origins = {"https://j10a609.p.ssafy.io", "http://localhost:3000"}, exposedHeaders = "*")
public class BoardController {
    private final BoardService boardService;
    private final TokenProvider tokenProvider;

    @GetMapping
    public ResponseEntity<List<BoardsResponse>> getAllBoards() {
        List<BoardsResponse> boardsResponses = boardService.findAllBoards();
        return ResponseEntity.ok(boardsResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardsResponse> getBoardById(@PathVariable String id) {
        return boardService.findBoardById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(HttpServletRequest httprequest, @RequestBody BoardRequest request) {
        String token = httprequest.getHeader(HttpHeaders.AUTHORIZATION);
        String userId = tokenProvider.extractId(token);
        Board createdBoard = boardService.createBoard(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Board> updateBoard(HttpServletRequest httprequest, @PathVariable String id, @RequestBody BoardRequest request) {
        String token = httprequest.getHeader(HttpHeaders.AUTHORIZATION);
        String userId = tokenProvider.extractId(token);
        Board updatedBoard = boardService.updateBoard(userId, id, request);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(HttpServletRequest httprequest, @PathVariable String id) {
        String token = httprequest.getHeader(HttpHeaders.AUTHORIZATION);
        String userId = tokenProvider.extractId(token);
        boardService.deleteBoard(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{boardId}/replies")
    public ResponseEntity<Reply> createReply(HttpServletRequest httprequest, @PathVariable String boardId, @RequestBody ReplyRequest request) {
        String token = httprequest.getHeader(HttpHeaders.AUTHORIZATION);
        String userId = tokenProvider.extractId(token);
        Reply reply = boardService.createReply(userId, boardId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reply);
    }

    @PatchMapping("/replies/{replyId}")
    public ResponseEntity<Reply> updateReply(HttpServletRequest httprequest, @PathVariable String replyId, @RequestBody ReplyRequest request) {
        String token = httprequest.getHeader(HttpHeaders.AUTHORIZATION);
        String userId = tokenProvider.extractId(token);
        Reply updatedReply = boardService.updateReply(userId, replyId, request);
        return ResponseEntity.ok(updatedReply);
    }

    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<?> deleteReply(HttpServletRequest httprequest, @PathVariable String replyId) {
        String token = httprequest.getHeader(HttpHeaders.AUTHORIZATION);
        String userId = tokenProvider.extractId(token);
        boardService.deleteReply(userId, replyId);
        return ResponseEntity.noContent().build();
    }
}
