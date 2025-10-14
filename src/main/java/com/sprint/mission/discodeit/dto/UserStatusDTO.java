package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public boolean isOnline() {

      Instant threshold = Instant.now().minus(5, ChronoUnit.MINUTES);
      return !lastActiveAt.isBefore(threshold);

    }

  }

  @Builder
  public record UpdateUserStatusCommand(UUID id, Instant lastActiveAt) {

  }

}
