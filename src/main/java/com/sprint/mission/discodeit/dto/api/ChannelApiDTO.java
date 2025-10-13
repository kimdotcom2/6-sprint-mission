package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.enums.ChannelType;
import java.time.Instant;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

public class ChannelApiDTO {

  @Builder
  public record PublicChannelCreateRequest(
      String name,
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
