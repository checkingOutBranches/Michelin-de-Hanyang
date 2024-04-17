package com.ssafy.michelin_de_hanyang.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserSign {
    private String id;
    private String password;
    private String name;
}
