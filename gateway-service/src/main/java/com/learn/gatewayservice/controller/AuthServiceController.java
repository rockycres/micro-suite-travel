package com.learn.gatewayservice.controller;


import com.learn.gatewayservice.model.AuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/authenticate")
@Slf4j
public class AuthServiceController {

    @RequestMapping("/login")
    public ResponseEntity<AuthenticationResponse> authorise(@AuthenticationPrincipal OidcUser oidcUser, Model model,
                                                            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client) {
        log.info("authorise endpoint");
        AuthenticationResponse authenticationResponse = null;
        try{
            authenticationResponse
                    = AuthenticationResponse.builder()
                    .userId(oidcUser.getEmail())
                    .accessToken(client.getAccessToken().getTokenValue())
                    .refreshToken(client.getRefreshToken().getTokenValue())
                    .expiresAt(client.getAccessToken().getExpiresAt().getEpochSecond())
                    .authorityList(oidcUser.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .build();
        }catch (Exception e){
            log.info("AuthController | login | error : " + e.getMessage());
        }


        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }



}