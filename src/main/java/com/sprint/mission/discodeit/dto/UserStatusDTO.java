package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.UUID;

public class UserStatusDTO {

  @Builder
  public record CreateUserStatusCommand(UUID userId) {

  }

  public static class UserStatus {

  }

    @Builder
    public record FindUserStatusResult(
            UUID id,
            UUID userId,
            Long lastActiveTimestamp,
            Long createdAt,
            Long updatedAt
    ) {

    }

    @Builder
    public record UpdateUserStatusCommand(UUID id, Long lastActiveTimestamp) {

    }

}
