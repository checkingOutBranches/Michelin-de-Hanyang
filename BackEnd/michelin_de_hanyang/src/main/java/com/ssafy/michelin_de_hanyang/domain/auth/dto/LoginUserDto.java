package com.ssafy.michelin_de_hanyang.domain.auth.dto;

import com.ssafy.michelin_de_hanyang.domain.user.dto.Role;

public class LoginUserDto {
    private String id;
    private String password;
    private String name;
    private Role role;
}
