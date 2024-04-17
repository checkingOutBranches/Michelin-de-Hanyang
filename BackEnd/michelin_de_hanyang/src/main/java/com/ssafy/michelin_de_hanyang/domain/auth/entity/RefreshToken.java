package com.ssafy.michelin_de_hanyang.domain.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * RefreshToken 객체의 직렬화와 역직렬화가 지원되도록 마커 인터페이스인 Serializable 인터페이스를 구현
 */
@Getter
@Builder
@AllArgsConstructor
@Document(collection = "jwtToken")
public class RefreshToken {

    @Id
    private String id;
    private String refreshToken;
}