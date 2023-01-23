package com.learn.gatewayservice.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
public class AuthenticationResponse {

    private String userId;
    private String email;
    private String accessToken;
    private String refreshToken;
    private Long expiresAt;
    private List<String> authorityList;

}