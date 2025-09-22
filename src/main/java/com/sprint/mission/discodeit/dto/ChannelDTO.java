package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.ChannelType;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

public class ChannelDTO {

    @Builder
    public record CreatePublicChannelCommand(String channelName, ChannelType category, boolean isVoiceChannel, String description) {

    }

    @Builder
    public record CreatePrivateChannelCommand(ChannelType category, boolean isVoiceChannel, List<UUID> userIdList, String description) {

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
            String description,
            Long createdAt,
            Long updatedAt
    ) {

    }

    //channel update를 위한 Request DTO
    @Builder
    public record UpdateChannelCommand(UUID id, String channelName, ChannelType category, boolean isVoiceChannel, List<UUID> userIdList, String description) {

    }

}
