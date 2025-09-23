package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.dto.UserStatusDTO;
import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO.UserUpdateRequest;
import com.sprint.mission.discodeit.enums.FileType;
import com.sprint.mission.discodeit.exception.AllReadyExistDataException;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import java.io.IOException;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final UserStatusService userStatusService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserApiDTO.FindUserResponse> signup(@RequestPart UserApiDTO.UserCreateRequest userCreateRequest, @RequestPart(value = "profile", required = false) MultipartFile profile)
        throws IOException {

        UserDTO.CreateUserCommand createUserCommand = UserDTO.CreateUserCommand.builder()
                .nickname(userCreateRequest.nickname())
                .email(userCreateRequest.email())
                .password(userCreateRequest.password())
                .description(userCreateRequest.description())
                .profileImage(profile.isEmpty() ? null : BinaryContentDTO.CreateBinaryContentCommand.builder()
                    .fileName(profile.getName())
                    .data(profile.getBytes())
                    .fileType(FileType.IMAGE)
                    .build())
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

    @PatchMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserApiDTO.FindUserResponse> updateUserProfile(@PathVariable UUID userId, @RequestPart UserUpdateRequest userUpdateRequest, @RequestPart(value = "profile", required = false) MultipartFile profile)
        throws IOException {

        UserDTO.UpdateUserCommand updateUserCommand = UserDTO.UpdateUserCommand.builder()
                .id(userId)
                .nickname(userUpdateRequest.nickname())
                .email(userUpdateRequest.email())
                .currentPassword(userUpdateRequest.currentPassword())
                .newPassword(userUpdateRequest.newPassword())
                .isProfileImageUpdated(!profile.isEmpty())
                .profileImage(profile.isEmpty() ? null : BinaryContentDTO.CreateBinaryContentCommand.builder()
                    .fileName(profile.getName())
                    .data(profile.getBytes())
                    .fileType(FileType.IMAGE)
                    .build())
                .build();

        userService.updateUser(updateUserCommand);

        UserDTO.FindUserResult user = userService.findUserById(userId)
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        return ResponseEntity.status(204).body(UserApiDTO.FindUserResponse.builder()
                .id(user.id())
                .nickname(user.nickname())
                .email(user.email())
                .profileImageId(user.profileImageId())
                .isOnline(user.isOnline())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.updatedAt()), ZoneId.systemDefault()))
                .build());

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {

        userService.deleteUserById(userId);

        return ResponseEntity.status(204).body("User deleted successfully");

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

    @GetMapping("/{userId}/userStatus")
    public ResponseEntity<UserApiDTO.CheckUserOnline> checkUserOnlineStatus(@PathVariable UUID userId) {

        UserDTO.FindUserResult user = userService.findUserById(userId).get();

        return ResponseEntity.ok(UserApiDTO.CheckUserOnline.builder()
                .id(user.id())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.updatedAt()), ZoneId.systemDefault()))
                .userId(user.id())
                .lastOnlineAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(user.updatedAt()), ZoneId.systemDefault()))
                .isOnline(true)
                .build());

    }

    @PatchMapping(value = "/{userId}/userStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserApiDTO.CheckUserOnline> updateUserOnlineStatus(@PathVariable UUID userId, @RequestBody UserApiDTO.UserStatusUpdateRequest userStatusUpdateRequest) {

        UserStatusDTO.FindUserStatusResult userStatus = userStatusService.findUserStatusByUserId(userId)
            .orElseThrow(() -> new NoSuchDataException("No such user status."));

        userStatusService.updateUserStatus(UserStatusDTO.UpdateUserStatusCommand.builder()
                .id(userStatus.id())
                .lastActiveTimestamp(userStatusUpdateRequest.newLastActiveAt().toEpochSecond(ZoneOffset.UTC))
                .build());

        return ResponseEntity.ok(UserApiDTO.CheckUserOnline.builder()
                .id(userStatus.id())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(userStatus.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(userStatus.updatedAt()), ZoneId.systemDefault()))
                .userId(userStatus.userId())
                .lastOnlineAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(userStatus.lastActiveTimestamp()), ZoneId.systemDefault()))
                .isOnline(true)
                .build());

    }

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> NoSuchDataException(NoSuchDataException e) {
        return ResponseEntity.status(404).body(ErrorApiDTO.ErrorApiResponse.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .message(e.getMessage())
            .build());
    }

    @ExceptionHandler(AllReadyExistDataException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> AllReadyExistDataException(AllReadyExistDataException e) {
        return ResponseEntity.status(400).body(ErrorApiDTO.ErrorApiResponse.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message(e.getMessage())
            .build());
    }

}
