package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MessageApiDTO {

  @Builder
  public record MessageCreateRequest(
      String content,
      UUID authorId,
      UUID channelId) {

  }

  @Builder
  public record MessageUpdateRequest(
      UUID id,
      @JsonProperty("newContent")
      String content) {

  }

  @Builder
  public record FindMessageResponse(
      UUID id,
      Instant createdAt,
      Instant updatedAt,
      UUID channelId,
      UUID authorId,
      String content,
      @JsonProperty("attachmentIds")
      List<UUID> attachmentIds
  ) {

  }

}
