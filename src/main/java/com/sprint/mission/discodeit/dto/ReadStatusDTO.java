package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import lombok.Builder;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ReadStatusDTO {

  @Builder
  public record CreateReadStatusCommand(UUID channelId, UUID userId, Instant lastReadTimeAt){

  }

  @Getter
  @Builder
  @RequiredArgsConstructor
  public static class ReadStatus {
    UUID id;
    Instant createdAt;
    Instant updatedAt;
    UUID userId;
    UUID channelId;
    Instant lastReadAt;
  }

  @Builder
  public record UpdateReadStatusCommand(UUID id, Instant lastReadAt){

  }

}
