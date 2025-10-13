package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Builder;
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
      String content,
      UUID channelId,
      UserApiDTO.FindUserResponse author,
      @JsonProperty("attachments")
      List<BinaryContentApiDTO.ReadBinaryContentResponse> attachments
  ) {

  }

}
