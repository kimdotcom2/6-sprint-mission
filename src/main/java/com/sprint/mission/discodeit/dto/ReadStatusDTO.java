package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.UUID;

public class ReadStatusDTO {

    @Builder
    public record CreateReadStatusRequest(UUID channelId, UUID userId, UUID messageId){

    }

    @Builder
    public record FindReadStatusResult(
            UUID id,
            UUID channelId,
            UUID userId,
            UUID messageId,
            Long lastReadTimestamp,
            Long createdAt,
            Long updatedAt
    ){

    }

    @Builder
    public record UpdateReadStatusRequest(UUID id){

    }

}
