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

        public boolean isEmailValid(String email) {
            return email.matches( "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        }

        public boolean isPasswordValid(String password) {
            //길이는 8자리 이상, 영어 대소문자, 숫자, 특수문자 포함
            return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
        }

        public SignUpRequest {

            if (!isPasswordValid(password) || !isEmailValid(email) || nickname.isBlank()) {
                throw new IllegalArgumentException("Invalid user data.");
            }

        }

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

        public boolean isEmailValid(String email) {
            return email.matches( "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        }

        public boolean isPasswordValid(String password) {
            //길이는 8자리 이상, 영어 대소문자, 숫자, 특수문자 포함
            return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
        }

        public UpdateUserProfileRequest {

            if (!isPasswordValid(newPassword) || !isEmailValid(email) || nickname.isBlank()) {
                throw new IllegalArgumentException("Invalid user data.");
            }

        }

    }

    @Builder
    public record DeleteUserRequest(String id) {

    }

    @Builder
    public record FindUserResponse(
            UUID id,
            String nickname,
            String email,
            String description,
            UUID profileImageId
    ) {

    }

}
