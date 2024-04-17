package com.ssafy.michelin_de_hanyang.domain.user.dto;

import lombok.Data;

@Data
public class InquiryRequest {
    private String name;
    private String email;
    private String content;
}
