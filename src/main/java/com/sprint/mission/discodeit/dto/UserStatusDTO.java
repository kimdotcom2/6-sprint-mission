package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.UUID;

public class UserStatusDTO {

    @Builder
    public record CreateReadStatusRequest(UUID userId) {

    }

    @Builder
    public record FindReadStatusRequest(
            UUID id,
            UUID userId,
            Long lastActiveTimestamp,
            Long createdAt,
            Long updatedAt
    ) {

    }

    @Builder
    public record UpdateReadStatusRequest(UUID id) {

    }

}
