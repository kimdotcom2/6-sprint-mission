package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.UUID;

public class UserStatusDTO {

    @Builder
    public record CreateReadStatusCommand(UUID userId) {

    }

    @Builder
    public record FindReadStatusResult(
            UUID id,
            UUID userId,
            Long lastActiveTimestamp,
            Long createdAt,
            Long updatedAt
    ) {

    }

    @Builder
    public record UpdateReadStatusCommand(UUID id) {

    }

}
