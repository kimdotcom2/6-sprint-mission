package com.sprint.mission.discodeit.dto.api;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;

public class ReadStatusApiDTO {

  @Builder
  public record ReadStatusCreateRequest(
      @NotBlank(message = "올바르지 않은 이용자입니다.") UUID userId,
      @NotBlank(message = "올바르지 않은 채널입니다.") UUID channelId,
      Instant lastReadAt) {

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
