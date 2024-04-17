package com.ssafy.michelin_de_hanyang.domain.board.service;

import com.ssafy.michelin_de_hanyang.domain.board.repository.ReplyRepository;
import com.ssafy.michelin_de_hanyang.domain.user.entity.User;
import com.ssafy.michelin_de_hanyang.domain.board.Board;
import com.ssafy.michelin_de_hanyang.domain.board.Reply;
import com.ssafy.michelin_de_hanyang.domain.board.dto.*;
import com.ssafy.michelin_de_hanyang.domain.board.repository.BoardRepository;
import com.ssafy.michelin_de_hanyang.domain.user.repository.UserRepository;
import com.ssafy.michelin_de_hanyang.global.error.exception.AuthorizationFailedException;
import com.ssafy.michelin_de_hanyang.global.error.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

//    private String getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new AuthenticationFailedException("사용자가 인증되지 않았습니다.");
//        }
//        return ((User) authentication.getPrincipal()).getId();
//    }

    public List<BoardsResponse> findAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(board -> {
                    String username = userRepository.findById(board.getUserId())
                            .map(User::getUsername)
                            .orElse("Unknown User");
                    return new BoardsResponse(board, username, Collections.emptyList());
                })
                .collect(Collectors.toList());
    }

    public Optional<BoardsResponse> findBoardById(String id) {
        return boardRepository.findById(id)
                .map(board -> {
                    String username = userRepository.findById(board.getUserId())
                            .map(User::getUsername)
                            .orElse("Unknown User");

                    List<ReplyResponse> replyResponseList = replyRepository.findAllById(board.getReplyIds()).stream()
                            .map(reply -> {
                                String replyUsername = userRepository.findById(reply.getUserId())
                                        .map(User::getUsername)
                                        .orElse("Unknown User");
                                return new ReplyResponse(reply, replyUsername);
                            })
                            .collect(Collectors.toList());
                    return new BoardsResponse(board, username, replyResponseList);
                });
    }

    public Board createBoard(String userId, BoardRequest request) {
        Board board = request.toEntity(userId);
        return boardRepository.save(board);
    }

    public Board updateBoard(String userId, String id, BoardRequest request) {
        return boardRepository.findById(id)
                .map(board -> {
                    if (!board.getUserId().equals(userId)) {
                        throw new AuthorizationFailedException("수정 권한이 없습니다.");
                    }
                    board.setTitle(request.getTitle());
                    board.setContent(request.getContent());
                    return boardRepository.save(board);
                })
                .orElseThrow(() -> new ResourceNotFoundException("게시글을 찾을 수 없습니다."));
    }

    public void deleteBoard(String userId, String id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("게시글을 찾을 수 없습니다."));
        if (!board.getUserId().equals(userId)) {
            throw new AuthorizationFailedException("삭제 권한이 없습니다.");
        }
        boardRepository.deleteById(id);
    }

    public Reply createReply(String userId, String boardId, ReplyRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("게시글을 찾을 수 없습니다."));

        Reply reply = request.toEntity(boardId, userId);
        Reply savedReply = replyRepository.save(reply);

        board.getReplyIds().add(savedReply.getId());
        boardRepository.save(board);

        return savedReply;
    }

    public Reply updateReply(String userId, String replyId, ReplyRequest request) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("댓글을 찾을 수 없습니다."));
        if (!reply.getUserId().equals(userId)) {
            throw new AuthorizationFailedException("댓글 수정 권한이 없습니다.");
        }
        reply.setReply(request.getReply());
        return replyRepository.save(reply);
    }

    public void deleteReply(String userId, String replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException("댓글을 찾을 수 없습니다."));
        if  (!reply.getUserId().equals(userId)) {
            throw new AuthorizationFailedException("댓글 삭제 권한이 없습니다.");
        }
        replyRepository.delete(reply);

        boardRepository.findAll().forEach(board -> {
            if(board.getReplyIds().removeIf(rId -> rId.equals(replyId))) {
                boardRepository.save(board);
            }
        });
    }
}