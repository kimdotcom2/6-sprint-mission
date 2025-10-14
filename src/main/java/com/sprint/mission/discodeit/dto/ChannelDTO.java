package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.ChannelType;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChannelDTO {

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Channel {

    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    private ChannelType type;
    private String name;
    private String description;
    private List<UserDTO.User> participants;
    private Instant lastMessageAt;

  }

  @Builder
  public record CreatePublicChannelCommand(String name, String description) {

  }

  @Builder
  public record CreatePrivateChannelCommand(ChannelType type, List<UUID> participants, String description) {

  }

  //channel update를 위한 Request DTO
  @Builder
  public record UpdateChannelCommand(UUID id, String name, ChannelType type,
                                     List<UUID> participants, String description) {

  }

}
