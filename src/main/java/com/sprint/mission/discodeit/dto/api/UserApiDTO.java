package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

public class UserApiDTO {

  @Builder
  public record UserCreateRequest(

      String username,
      String email,
      String password,
      String description) {

  }

  @Builder
  public record UserUpdateRequest(
      @JsonProperty("newUsername")
      String username,
      @JsonProperty("newEmail")
      String email,
      String currentPassword,
      String newPassword
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
  public record CheckUserOnline(
      UUID id,
      Instant createdAt,
      Instant updatedAt,
      UUID userId,
      @JsonProperty("lastActiveAt")
      Instant lastOnlineAt,
      boolean isOnline
  ) {

  }


}
