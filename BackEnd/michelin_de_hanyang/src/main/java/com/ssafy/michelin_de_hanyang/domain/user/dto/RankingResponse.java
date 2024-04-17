package com.ssafy.michelin_de_hanyang.domain.user.dto;

import lombok.Data;

@Data
public class RankingResponse {
    private String id;
    private int questSuccess;
    private int lv;
    private String username;
    private int exp;
}
