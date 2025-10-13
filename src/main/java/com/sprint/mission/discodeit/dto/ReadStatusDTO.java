package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import lombok.Builder;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ReadStatusDTO {

  @Builder
  public record CreateReadStatusCommand(UUID channelId, UUID userId, Instant lastReadTimeAt) {

  }

  @Getter
  @Builder
  @RequiredArgsConstructor
  public static class ReadStatus {

    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID userId;
    private UUID channelId;
    private Instant lastReadAt;

  }

  @Builder
  public record UpdateReadStatusCommand(UUID id, Instant lastReadAt) {

  }

}
