package com.ssafy.michelin_de_hanyang.domain.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reply")
public class Reply {
    @Id
    private String id;
    private String reply;
    private LocalDateTime createdAt;

    private String userId;
    private String boardId;
}

