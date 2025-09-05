package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.ChannelType;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

public class ChannelDTO {

    @Builder
    public record CreatePublicChannelRequest(String channelName, ChannelType category, boolean isVoiceChannel) {

    }

    @Builder
    public record CreatePrivateChannelRequest(ChannelType category, boolean isVoiceChannel, List<UUID> userIdList) {

    }

    @Builder
    public record FindChannelResult(
            UUID id,
            String channelName,
            ChannelType category,
            boolean isVoiceChannel,
            boolean isPrivate,
            List<UUID> userIdList,
            Long recentMessageTime,
            Long createdAt,
            Long updatedAt
    ) {

    }

    //channel update를 위한 Request DTO
    @Builder
    public record UpdateChannelRequest(UUID id, String channelName, ChannelType category, boolean isVoiceChannel) {

    }

}
