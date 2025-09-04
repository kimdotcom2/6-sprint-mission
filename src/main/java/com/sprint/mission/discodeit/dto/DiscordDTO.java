package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.ChannelType;
import lombok.Builder;

import java.util.UUID;

public class DiscordDTO {

    //user update를 위한 Request DTO
    @Builder
    public record UpdateUserRequest(UUID id, String nickname, String email, String currentPassword, String newPassword, String description) {

    }

    //channel update를 위한 Request DTO
    @Builder
    public record UpdateChannelRequest(UUID id, String channelName, ChannelType category, boolean isVoiceChannel) {

    }

    //message update를 위한 Request DTO
    @Builder
    public record UpdateMessageRequest(UUID id, String content, boolean isReply, UUID parentMessageId) {

    }

}
