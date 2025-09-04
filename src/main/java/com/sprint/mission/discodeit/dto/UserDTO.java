package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.FileType;
import lombok.Builder;

import java.util.UUID;

public class UserDTO {

    //login request DTO
    @Builder
    public record LoginRequest(String email, String password) {

    }

    @Builder
    public record CreateUserRequest(
            String nickname,
            String email,
            String password,
            String description,
            byte[] profileImage,
            FileType fileType) {

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
            byte[] profileImage,
            FileType fileType
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
