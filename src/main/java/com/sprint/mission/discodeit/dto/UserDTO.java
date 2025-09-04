package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.UUID;

public class UserDTO {

    //login request DTO
    @Builder
    public record LoginRequest(String email, String password) {

    }

    //user update를 위한 Request DTO
    @Builder
    public record UpdateUserRequest(
            UUID id,
            String nickname,
            String email,
            String currentPassword,
            String newPassword,
            String description,
            boolean isProfileImageUpdated,
            UUID profileImageId
    ) {

    }

    //find user을 위한 DTO
    @Builder
    public record FindUserResult(
            UUID id,
            String nickname,
            String email,
            String description,
            UUID profileImageId,
            boolean isOnline
    ) {

    }

}
