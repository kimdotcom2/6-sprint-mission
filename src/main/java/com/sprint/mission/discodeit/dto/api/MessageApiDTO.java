package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

public class MessageApiDTO {

  @Builder
  public record MessageCreateRequest(
      @NotBlank(message = "메시지는 공백을 허용하지 않습니다.") String content,
      @NotBlank(message = "올바르지 않은 이용자입니다.") UUID authorId,
      @NotBlank(message = "올바르지 않은 채널입니다.") UUID channelId) {

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
