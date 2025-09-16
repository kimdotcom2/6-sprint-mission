package com.sprint.mission.discodeit.dto.api;

import com.sprint.mission.discodeit.enums.FileType;
import lombok.Builder;

import java.util.UUID;

public class UserApiDTO {

    @Builder
    public record SignUpRequest(
            String nickname,
            String email,
            String password,
            String description,
            byte[] profileImage,
            FileType fileType) {

    }

    @Builder
    public record UpdateUserProfileRequest(
            UUID id,
            String nickname,
            String email,
            String currentPassword,
            String newPassword,
            String description,
            boolean isProfileImageUpdated,
            byte[] profileImage,
            FileType fileType
    ) {

    }

    @Builder
    public record DeleteUserRequest(UUID id) {

    }

    @Builder
    public record FindUserResponse(
            UUID id,
            String nickname,
            String email,
            String description,
            UUID profileImageId,
            boolean isOnline,
            Long createdAt,
            Long updatedAt
    ) {

    }

    @Builder
    public record CheckUserOnline(boolean isOnline) {

    }

}
