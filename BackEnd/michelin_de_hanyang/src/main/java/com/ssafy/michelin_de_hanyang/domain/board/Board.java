package com.ssafy.michelin_de_hanyang.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "board")
public class Board {
    @Id
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    private String userId;

    private List<String> replyIds = new ArrayList<>();
}