package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import lombok.Builder;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserStatusDTO {

  @Builder
  public record CreateUserStatusCommand(UUID userId) {

  }

  @Getter
  @RequiredArgsConstructor
  @Builder
  public static class UserStatus {

    private UUID id;
    private UUID userId;
    private Instant lastActiveAt;

  }

  @Builder
  public record UpdateUserStatusCommand(UUID id, Instant lastActiveAt) {

  }

}
