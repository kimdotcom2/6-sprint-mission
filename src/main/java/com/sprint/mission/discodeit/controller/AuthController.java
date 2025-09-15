package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.dto.api.AuthApiDTO;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @RequestMapping(value = "/api/auth/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody AuthApiDTO.LoginRequest loginRequest) {

        authService.login(UserDTO.LoginCommand.builder()
                .nickname(loginRequest.nickname())
                .password(loginRequest.password())
                .build());

        return ResponseEntity.ok("Login successfully");
    }

}
