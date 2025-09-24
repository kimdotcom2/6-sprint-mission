package com.sprint.mission.discodeit.dto.api;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReadStatusApiDTO {

    @Builder
    public record ReadStatusCreateRequest(UUID userId, UUID channelId, LocalDateTime lastReadAt){

    }

    @Builder
    public record ReadStatusUpdateRequest(LocalDateTime newLastReadAt){

    }

    @Builder
    public record FindReadStatusResponse(
            UUID id,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            UUID userId,
            UUID channelId,
            LocalDateTime lastReadAt
    ){

    }

}
