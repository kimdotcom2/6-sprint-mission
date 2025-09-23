package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.enums.FileType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserApiDTO {

    @Builder
    public record UserCreateRequest(
            @JsonProperty("username")
            String nickname,
            String email,
            String password,
            String description,
            byte[] profileImage,
            FileType fileType) {

    }

    @Builder
    public record UserUpdateRequest(
            @JsonProperty("newUsername")
            String nickname,
            @JsonProperty("newEmail")
            String email,
            String currentPassword,
            String newPassword
    ) {

    }

    @Builder
    public record UserStatusUpdateRequest(LocalDateTime newLastActiveAt) {

    }

    @Builder
    public record FindUserResponse(
            UUID id,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            @JsonProperty("username")
            String nickname,
            String email,
            @JsonProperty("profileId")
            UUID profileImageId,
            @JsonProperty("online")
            boolean isOnline
    ) {

    }

    @Builder
    public record CheckUserOnline(
        UUID id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UUID userId,
        @JsonProperty("lastActiveAt")
        LocalDateTime lastOnlineAt,
        boolean isOnline
    ) {

    }


}
