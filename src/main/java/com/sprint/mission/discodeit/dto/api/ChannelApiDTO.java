package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.enums.ChannelType;
import java.time.Instant;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

public class ChannelApiDTO {

    @Builder
    public record PublicChannelCreateRequest(
        String name,
        String category,
        String description) {

    }

    @Builder
    public record PrivateChannelCreateRequest(
        @JsonProperty("participantIds")
        List<UUID> participantIdList
    ) {

    }

    @Builder
    public record ChannelUpdateRequest(
        @JsonProperty("newName")
        String name,
        String category,
        @JsonProperty("newDescription")
        String description) {

    }

    @Builder
    public record FindChannelResponse(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            ChannelType type,
            @JsonProperty("name")
            String name,
            String description,
            @JsonProperty("participantIds")
            List<UUID> participantIdList,
            Long recentMessageTime
    ) {

    }

}
