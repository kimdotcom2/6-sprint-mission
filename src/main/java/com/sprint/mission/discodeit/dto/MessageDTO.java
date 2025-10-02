package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.dto.BinaryContentDTO.BinaryContentCreateCommand;
import java.time.Instant;
import lombok.Builder;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class MessageDTO {

  @Builder
  public record CreateMessageCommand(String content, UUID channelId, UUID userId, List<BinaryContentCreateCommand> binaryContentList) {

  }

  @Getter
  @Builder
  @RequiredArgsConstructor
  public static class Message {

    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    private ChannelDTO.Channel channel;
    private UserDTO.User author;
    private String content;
    private List<BinaryContentDTO.BinaryContent> attachments;

  }

  //message update를 위한 Request DTO
  @Builder
  public record UpdateMessageCommand(UUID id, String content) {

  }

}
