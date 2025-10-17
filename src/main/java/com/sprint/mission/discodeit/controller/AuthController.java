package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.dto.api.AuthApiDTO;
import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import com.sprint.mission.discodeit.mapper.api.AuthApiMapper;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 관련 API 컨트롤러
 */
@Tag(name = "인증 API", description = "사용자 인증 관련 API")
@RestController
@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final UserService userService;
  private final AuthApiMapper authApiMapper;

  /**
   * 사용자 로그인
   *
   * @param loginRequest 로그인 요청 정보
   * @return 로그인된 사용자 정보
   */
  @Operation(
      summary = "사용자 로그인",
      description = "사용자 인증을 수행하고 JWT 토큰을 발급합니다.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "로그인 성공",
              content = @Content(schema = @Schema(implementation = UserApiDTO.FindUserResponse.class))
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
          ),
          @ApiResponse(
              responseCode = "404",
              description = "사용자를 찾을 수 없음",
              content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
          )
      }
  )
  @PostMapping(value = "/login")
  public ResponseEntity<UserApiDTO.FindUserResponse> login(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "로그인 요청 정보",
          required = true,
          content = @Content(schema = @Schema(implementation = AuthApiDTO.LoginRequest.class))
      )
      @RequestBody @Valid AuthApiDTO.LoginRequest loginRequest) {

    UserDTO.User user = authService.login(UserDTO.LoginCommand.builder()
        .username(loginRequest.username())
        .password(loginRequest.password())
        .build());

    return ResponseEntity.ok(authApiMapper.userToFindUserResponse(user));

  }

  @ExceptionHandler(NoSuchDataBaseRecordException.class)
  public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleNoSuchDataBaseRecordException(
      NoSuchDataBaseRecordException e) {

    log.error("NoSuchDataBaseRecordException occurred", e);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorApiDTO.ErrorApiResponse.builder()
        .code(HttpStatus.NOT_FOUND.value())
        .message(e.getMessage())
        .build());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleIllegalArgumentException(
      IllegalArgumentException e) {

    log.error("IllegalArgumentException occurred", e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorApiDTO.ErrorApiResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(e.getMessage())
        .build());
  }

}
