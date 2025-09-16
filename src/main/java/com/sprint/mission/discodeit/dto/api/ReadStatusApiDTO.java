package com.sprint.mission.discodeit.dto.api;

import lombok.Builder;

import java.util.UUID;

public class ReadStatusApiDTO {

    @Builder
    public record CreateReadStatusRequest(UUID channelId, UUID userId){

    }

    @Builder
    public record UpdateReadStatusRequest(UUID id){

    }

    @Builder
    public record FindReadStatusResponse(
            UUID id,
            UUID channelId,
            UUID userId,
            Long lastReadTimestamp
    ){

    }

}
