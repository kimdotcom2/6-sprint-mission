package com.sprint.mission.discodeit.mapper.api;

import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.dto.api.BinaryContentApiDTO;
import com.sprint.mission.discodeit.dto.api.MessageApiDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO;
import org.springframework.stereotype.Component;

@Component
public class MessageApiMapper {

  public MessageApiDTO.FindMessageResponse messageToFindMessageResponse(MessageDTO.Message message) {

    return MessageApiDTO.FindMessageResponse.builder()
        .id(message.getId())
        .createdAt(message.getCreatedAt())
        .updatedAt(message.getUpdatedAt())
        .content(message.getContent())
        .channelId(message.getChannelId())
        .author(UserApiDTO.FindUserResponse.builder()
            .id(message.getAuthor().getId())
            .username(message.getAuthor().getUsername())
            .email(message.getAuthor().getEmail())
            .profile(BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                .id(message.getAuthor().getProfileId().getId())
                .fileName(message.getAuthor().getProfileId().getFileName())
                .size(message.getAuthor().getProfileId().getSize())
                .contentType(message.getAuthor().getProfileId().getContentType())
                .build())
            .isOnline(message.getAuthor().getIsOnline())
            .build())
        .attachments(message.getAttachments().stream().map(attachment ->
            BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .size(attachment.getSize())
                .contentType(attachment.getContentType())
                .build()
        ).toList())
        .build();

  }

}
