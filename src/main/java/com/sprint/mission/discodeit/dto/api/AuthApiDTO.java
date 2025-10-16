package com.sprint.mission.discodeit.dto.api;

import jakarta.validation.constraints.NotBlank;

public class AuthApiDTO {

  public record LoginRequest(
      @NotBlank(message = "계정명을 입력하세요.") String username,
      @NotBlank(message = "비밀번호를 입력하세요.") String password) {

  }

}
