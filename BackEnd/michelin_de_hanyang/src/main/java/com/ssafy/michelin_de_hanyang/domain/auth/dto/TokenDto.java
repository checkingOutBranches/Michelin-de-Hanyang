package com.ssafy.michelin_de_hanyang.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
