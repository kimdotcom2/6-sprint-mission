package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.ChannelType;
import lombok.Builder;

import java.util.UUID;

public class ChannelDTO {

    @Builder
    public record CreatePublicChannelRequest(String channelName, ChannelType category, boolean isVoiceChannel) {

    }

    @Builder
    public record CreatePrivateChannelRequest(String channelName, ChannelType category, boolean isVoiceChannel) {

    }

    //channel update를 위한 Request DTO
    @Builder
    public record UpdateChannelRequest(UUID id, String channelName, ChannelType category, boolean isVoiceChannel) {

    }

}
