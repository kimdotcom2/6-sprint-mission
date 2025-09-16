package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.UUID;

public class ReadStatusDTO {

    @Builder
    public record CreateReadStatusCommand(UUID channelId, UUID userId){

    }

    @Builder
    public record FindReadStatusResult(
            UUID id,
            UUID channelId,
            UUID userId,
            Long lastReadTimestamp,
            Long createdAt,
            Long updatedAt
    ){

    }

    @Builder
    public record UpdateReadStatusCommand(UUID id){

    }

}
