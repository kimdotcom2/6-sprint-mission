package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;

public class UserApiDTO {

  @Builder
  public record UserCreateRequest(
      @NotBlank(message = "계정명을 입력하세요.") String username,
      @NotBlank(message = "이메일을 입력하세요.") String email,
      @NotBlank(message = "비밀번호를 입력하세요.") String password,
      String description) {

  }

  @Builder
  public record UserUpdateRequest(
      @JsonProperty("newUsername")
      @NotBlank(message = "계정명을 입력하세요.")
      String username,
      @JsonProperty("newEmail")
      @NotBlank(message = "이메일을 입력하세요.")
      String email,
      @NotBlank(message = "기존 비밀번호를 입력하세요.")String currentPassword,
      @NotBlank(message = "새 비밀번호를 입력하세요.")String newPassword
  ) {

  }

  @Builder
  public record UserStatusUpdateRequest(Instant newLastActiveAt) {

  }

  @Builder
  public record FindUserResponse(
      UUID id,
      String username,
      String email,
      @JsonProperty("profile")
      BinaryContentApiDTO.ReadBinaryContentResponse profile,
      @JsonProperty("online")
      boolean isOnline
  ) {

  }

  @Builder
  public record CheckUserOnlineResponse(
      UUID id,
      UUID userId,
      @JsonProperty("lastActiveAt")
      Instant lastOnlineAt,
      boolean isOnline
  ) {

  }


}
