package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.FileType;
import lombok.Builder;

import java.util.UUID;

public class UserDTO {

    //login request DTO
    @Builder
    public record LoginCommand(String nickname, String password) {

    }

    @Builder
    public record CreateUserCommand(
            String nickname,
            String email,
            String password,
            String description,
            BinaryContentDTO.CreateBinaryContentCommand profileImage) {

        public boolean isEmailValid(String email) {
            return email.matches( "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        }

        public boolean isPasswordValid(String password) {
            //길이는 8자리 이상, 영어 대소문자, 숫자, 특수문자 포함
            return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
        }

        public CreateUserCommand {

            if (!isPasswordValid(password) || !isEmailValid(email) || nickname.isBlank()) {
                throw new IllegalArgumentException("Invalid user data.");
            }

        }

    }

    //find user을 위한 DTO
    @Builder
    public record FindUserResult(
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

    //user update를 위한 Request DTO
    @Builder
    public record UpdateUserCommand(
            UUID id,
            String nickname,
            String email,
            String currentPassword,
            String newPassword,
            String description,
            boolean isProfileImageUpdated,
            BinaryContentDTO.CreateBinaryContentCommand profileImage
    ) {

        public boolean isEmailValid(String email) {
            return email.matches( "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        }

        public boolean isPasswordValid(String password) {
            //길이는 8자리 이상, 영어 대소문자, 숫자, 특수문자 포함
            return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
        }

        public UpdateUserCommand {

            if (!isPasswordValid(newPassword) || !isEmailValid(email) || nickname.isBlank()) {
                throw new IllegalArgumentException("Invalid user data.");
            }

        }

    }

}
