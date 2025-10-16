package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.enums.ChannelType;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

public class ChannelApiDTO {

  @Builder
  public record PublicChannelCreateRequest(
      @NotBlank(message = "채널 이름을 입력하세요.") String name,
      String description) {

  }

  @Builder
  public record PrivateChannelCreateRequest(
      @JsonProperty("participantIds")
      List<UUID> participantIdList
  ) {

  }

  @Builder
  public record ChannelUpdateRequest(
      @JsonProperty("newName")
      @NotBlank(message = "채널 이름을 입력하세요.")
      String name,
      @JsonProperty("newDescription")
      String description) {

  }

  @Builder
  public record FindChannelResponse(
      UUID id,
      ChannelType type,
      String name,
      String description,
      List<UserApiDTO.FindUserResponse> participants,
      Instant lastMessageAt
  ) {

  }


}
