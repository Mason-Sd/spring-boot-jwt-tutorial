package com.mason.jwttutorial.controller;

import com.mason.jwttutorial.dto.LoginDto;
import com.mason.jwttutorial.dto.TokenDto;
import com.mason.jwttutorial.jwt.JwtFilter;
import com.mason.jwttutorial.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // authenticationToken을 이용해서 Authentication 객체를 생성하려고, authenticate 메서드가 실행될 때
        // customUserDetailsService 내 loadUserByUsername 메서드가 실행된다
        // 즉 DB에서 username으로 조회를 한 결과의 UserDetails를 가져와서 Authentication 객체를 생성한다.
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // authenticaiton 객체 정보를 security Context에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // authentication객체를 기반으로 createToken으로 jwt를 생성한다
        String jwt = tokenProvider.createToken(authentication);

        // jwt는 Response Header에 넣어주고
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // response body에도 TokenDto로 묶어서 바디에 넣어 리턴해준다
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}