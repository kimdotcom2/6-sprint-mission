package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.dto.BinaryContentDTO.BinaryContentCreateCommand;
import java.time.Instant;
import lombok.Builder;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UserDTO {

  //login request DTO
  @Builder
  public record LoginCommand(String username, String password) {

  }

  @Getter
  @Builder
  @RequiredArgsConstructor
  public static class User {

    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    private String username;
    private String email;
    private String password;
    private BinaryContentDTO.BinaryContent profileId;
    private UserStatusDTO.UserStatus userStatus;

  }

  @Builder
  public record CreateUserCommand(
      String username,
      String email,
      String password,
      String description,
      BinaryContentCreateCommand profileImage) {

      public boolean isEmailValid(String email) {
        return email.matches( "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
      }

      public boolean isPasswordValid(String password) {
        //길이는 8자리 이상, 영어 대소문자, 숫자, 특수문자 포함
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
      }

      public CreateUserCommand {

        if (!isPasswordValid(password) || !isEmailValid(email) || username.isBlank()) {
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
            String username,
            String email,
            String currentPassword,
            String newPassword,
            String description,
            boolean isProfileImageUpdated,
            BinaryContentCreateCommand profileImage
    ) {

        public boolean isEmailValid(String email) {
            return email.matches( "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        }

        public boolean isPasswordValid(String password) {
            //길이는 8자리 이상, 영어 대소문자, 숫자, 특수문자 포함
            return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
        }

        public UpdateUserCommand {

            if (!isPasswordValid(newPassword) || !isEmailValid(email) || username.isBlank()) {
                throw new IllegalArgumentException("Invalid user data.");
            }

        }

    }

}
