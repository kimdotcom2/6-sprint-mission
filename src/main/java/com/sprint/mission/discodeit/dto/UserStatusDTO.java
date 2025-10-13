package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class UserStatusDTO {

  @Builder
  public record CreateUserStatusCommand(UUID userId) {

  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UserStatus {

    private UUID id;
    private UUID userId;
    private Instant lastActiveAt;

  }

  @Builder
  public record UpdateUserStatusCommand(UUID id, Instant lastActiveAt) {

  }

}
