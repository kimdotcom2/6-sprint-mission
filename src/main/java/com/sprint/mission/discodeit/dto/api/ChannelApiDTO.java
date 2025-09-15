package com.sprint.mission.discodeit.dto.api;

import com.sprint.mission.discodeit.enums.ChannelType;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

public class ChannelApiDTO {

    @Builder
    public record CreatePublicChannelRequest(String channelName, String category, boolean isVoiceChannel) {

    }

    @Builder
    public record CreatePrivateChannelRequest(ChannelType category, boolean isVoiceChannel, List<UUID> userIdList) {

    }

    @Builder
    public record UpdateChannelRequest(UUID id, String channelName, String category, boolean isVoiceChannel) {

    }

    @Builder
    public record DeleteChannelRequest(UUID id) {

    }

    @Builder
    public record FindChannelResponse(
            UUID id,
            String channelName,
            ChannelType category,
            boolean isVoiceChannel,
            boolean isPrivate,
            List<UUID> userIdList,
            Long recentMessageTime
    ) {

    }

}
