package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.dto.UserStatusDTO;
import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO;
import com.sprint.mission.discodeit.exception.AllReadyExistDataException;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final UserStatusService userStatusService;

    @PostMapping()
    public ResponseEntity<UserApiDTO.FindUserResponse> signup(@RequestBody UserApiDTO.UserCreateRequest userCreateRequest) {

        UserDTO.CreateUserCommand createUserCommand = UserDTO.CreateUserCommand.builder()
                .nickname(userCreateRequest.nickname())
                .email(userCreateRequest.email())
                .password(userCreateRequest.password())
                .description(userCreateRequest.description())
                .profileImage(userCreateRequest.profileImage())
                .fileType(userCreateRequest.fileType())
                .build();

        userService.createUser(createUserCommand);

        UserDTO.FindUserResult user = userService.findUserByEmail(userCreateRequest.email())
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        return ResponseEntity.status(201).body(UserApiDTO.FindUserResponse.builder()
                .id(user.id())
                .nickname(user.nickname())
                .email(user.email())
                .profileImageId(user.profileImageId())
                .isOnline(user.isOnline())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.updatedAt()), ZoneId.systemDefault()))
                .build());

    }

    @PutMapping()
    public ResponseEntity<String> updateUserProfile(@RequestBody UserApiDTO.UpdateUserProfileRequest updateUserProfileRequest) {

        UserDTO.UpdateUserCommand updateUserCommand = UserDTO.UpdateUserCommand.builder()
                .id(updateUserProfileRequest.id())
                .nickname(updateUserProfileRequest.nickname())
                .email(updateUserProfileRequest.email())
                .currentPassword(updateUserProfileRequest.currentPassword())
                .newPassword(updateUserProfileRequest.newPassword())
                .description(updateUserProfileRequest.description())
                .isProfileImageUpdated(updateUserProfileRequest.isProfileImageUpdated())
                .profileImage(updateUserProfileRequest.profileImage())
                .fileType(updateUserProfileRequest.fileType())
                .build();

        userService.updateUser(updateUserCommand);

        return ResponseEntity.ok("User updated successfully");

    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUser(@RequestBody UserApiDTO.DeleteUserRequest deleteUserRequest) {

        userService.deleteUserById(deleteUserRequest.id());

        return ResponseEntity.ok("User deleted successfully");

    }

    @GetMapping()
    public ResponseEntity<List<UserApiDTO.FindUserResponse>> findAll() {

        List<UserApiDTO.FindUserResponse> userList = userService.findAllUsers().stream()
                .map(user -> UserApiDTO.FindUserResponse.builder()
                        .id(user.id())
                        .nickname(user.nickname())
                        .email(user.email())
                        .profileImageId(user.profileImageId())
                        .isOnline(user.isOnline())
                        .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.createdAt()), ZoneId.systemDefault()))
                        .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.updatedAt()), ZoneId.systemDefault()))
                        .build())

                .toList();

        return ResponseEntity.status(201).body(userList);

    }

    @GetMapping("/{userId}/online-status")
    public ResponseEntity<UserApiDTO.CheckUserOnline> checkUserOnlineStatus(@PathVariable UUID userId) {

        UserDTO.FindUserResult user = userService.findUserById(userId).get();

        return ResponseEntity.ok(UserApiDTO.CheckUserOnline.builder()
                .isOnline(user.isOnline())
                .build());

    }

    @PutMapping("/{userId}/online-status-update")
    public ResponseEntity<String> updateUserOnlineStatus(@PathVariable UUID userId) {

        UserStatusDTO.FindUserStatusResult findUserResult = userStatusService.findUserStatusByUserId(userId).get();

        userStatusService.updateUserStatus(UserStatusDTO.UpdateUserStatusCommand.builder()
                .id(findUserResult.id())
                .build());

        return ResponseEntity.ok("User online status updated successfully");

    }

    @ExceptionHandler(AllReadyExistDataException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> AllReadyExistDataException(AllReadyExistDataException e) {

        return ResponseEntity.status(400).body(ErrorApiDTO.ErrorApiResponse.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message(e.getMessage())
            .build());

    }

}
