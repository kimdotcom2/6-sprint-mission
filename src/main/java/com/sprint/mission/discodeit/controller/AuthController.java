package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.dto.api.AuthApiDTO;
import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.UserService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserApiDTO.FindUserResponse> login(@RequestBody AuthApiDTO.LoginRequest loginRequest) {

        authService.login(UserDTO.LoginCommand.builder()
                .nickname(loginRequest.nickname())
                .password(loginRequest.password())
                .build());

        UserDTO.FindUserResult findUserResult = userService.findUserByNickname(loginRequest.nickname())
            .orElseThrow(() -> new NoSuchDataException("No such user"));

        return ResponseEntity.ok(UserApiDTO.FindUserResponse.builder()
            .id(findUserResult.id())
            .nickname(findUserResult.nickname())
            .email(findUserResult.email())
            .profileImageId(findUserResult.profileImageId())
            .isOnline(findUserResult.isOnline())
            .createdAt(
                LocalDateTime.ofInstant(Instant.ofEpochMilli(findUserResult.createdAt()), ZoneId.systemDefault()))
            .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(findUserResult.updatedAt()), ZoneId.systemDefault()))
            .build());
    }

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleNoSuchDataException(NoSuchDataException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorApiDTO.ErrorApiResponse.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .message(e.getMessage())
            .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorApiDTO.ErrorApiResponse.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message(e.getMessage())
            .build());
    }

}
