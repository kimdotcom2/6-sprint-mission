package com.sprint.mission.discodeit.mapper.api;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.dto.api.BinaryContentApiDTO;
import com.sprint.mission.discodeit.dto.api.ChannelApiDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class ChannelApiMapper {

  public ChannelApiDTO.FindChannelResponse channelToFindChannelResponse(ChannelDTO.Channel channel) {
    return ChannelApiDTO.FindChannelResponse.builder()
        .id(channel.getId())
        .type(channel.getType())
        .name(channel.getName())
        .description(channel.getDescription())
        .participants(channel.getParticipants() != null ? channel.getParticipants().stream().map(user -> UserApiDTO.FindUserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .profile(user.getProfileId() != null ? BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                .id(user.getProfileId().getId())
                .fileName(user.getProfileId().getFileName())
                .size(user.getProfileId().getSize())
                .contentType(user.getProfileId().getContentType())
                .build() : null)
            .isOnline(user.getIsOnline())
            .build()).toList() : new ArrayList<>())
        .lastMessageAt(channel.getLastMessageAt() != null ? channel.getLastMessageAt() : null)
        .build();
  }

}
