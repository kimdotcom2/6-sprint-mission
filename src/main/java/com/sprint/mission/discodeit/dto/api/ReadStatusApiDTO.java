package com.sprint.mission.discodeit.dto.api;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;

public class ReadStatusApiDTO {

  @Builder
  public record ReadStatusCreateRequest(UUID userId, UUID channelId, Instant lastReadAt) {

  }

  @Builder
  public record ReadStatusUpdateRequest(Instant newLastReadAt) {

  }

  @Builder
  public record FindReadStatusResponse(
      UUID id,
      UUID userId,
      UUID channelId,
      Instant lastReadAt
  ) {

  }

}
