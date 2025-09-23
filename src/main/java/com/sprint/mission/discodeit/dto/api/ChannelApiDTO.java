package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.enums.ChannelType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ChannelApiDTO {

    @Builder
    public record PublicChannelCreateRequest(
        @JsonProperty("name")
        String channelName,
        String category,
        boolean isVoiceChannel,
        String description) {

    }

    @Builder
    public record PrivateChannelCreateRequest(
        //ChannelType category,
        //boolean isVoiceChannel,
        @JsonProperty("participantIds")
        List<UUID> userIdList
        //String description
    ) {

    }

    @Builder
    public record ChannelUpdateRequest(
        @JsonProperty("newName")
        String channelName,
        String category,
        boolean isVoiceChannel,
        @JsonProperty("newDescription")
        String description) {

    }

    @Builder
    public record FindChannelResponse(
            UUID id,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            ChannelType category,
            boolean isVoiceChannel,
            String type,
            @JsonProperty("name")
            String channelName,
            String description,
            @JsonProperty("participantIds")
            List<UUID> userIdList,
            Long recentMessageTime
    ) {

    }

}
