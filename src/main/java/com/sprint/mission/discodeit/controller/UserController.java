package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.dto.UserStatusDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final UserStatusService userStatusService;

    @RequestMapping(value = "/api/user/signup", method = RequestMethod.POST)
    public ResponseEntity<String> signup(@RequestBody UserApiDTO.SignUpRequest signUpRequest) {

        UserDTO.CreateUserCommand createUserCommand = UserDTO.CreateUserCommand.builder()
                .nickname(signUpRequest.nickname())
                .email(signUpRequest.email())
                .password(signUpRequest.password())
                .description(signUpRequest.description())
                .profileImage(signUpRequest.profileImage())
                .fileType(signUpRequest.fileType())
                .build();

        userService.createUser(createUserCommand);

        return ResponseEntity.ok("User created successfully");

    }

    @RequestMapping(value = "/api/user/update", method = RequestMethod.PUT)
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

    @RequestMapping(value = "/api/user/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@RequestBody UserApiDTO.DeleteUserRequest deleteUserRequest) {

        userService.deleteUserById(deleteUserRequest.id());

        return ResponseEntity.ok("User deleted successfully");

    }

    @RequestMapping(value = "/api/user/all", method = RequestMethod.GET)
    public List<UserApiDTO.FindUserResponse> findAllUsers() {

        return userService.findAllUsers().stream()
                .map(user -> UserApiDTO.FindUserResponse.builder()
                        .id(user.id())
                        .nickname(user.nickname())
                        .email(user.email())
                        .description(user.description())
                        .profileImageId(user.profileImageId())
                        .isOnline(user.isOnline())
                        .createdAt(user.createdAt())
                        .updatedAt(user.updatedAt())
                        .build())

                .toList();

    }

    @RequestMapping(value = "/api/user/{userId}/online-status", method = RequestMethod.GET)
    public ResponseEntity<UserApiDTO.CheckUserOnline> checkUserOnlineStatus(@PathVariable UUID userId) {

        UserDTO.FindUserResult user = userService.findUserById(userId).get();

        return ResponseEntity.ok(UserApiDTO.CheckUserOnline.builder()
                .isOnline(user.isOnline())
                .build());

    }

    @RequestMapping(value = "/api/user/{userId}/online-status-update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUserOnlineStatus(@PathVariable UUID userId) {

        UserStatusDTO.FindUserStatusResult findUserResult = userStatusService.findUserStatusByUserId(userId).get();

        userStatusService.updateUserStatus(UserStatusDTO.UpdateUserStatusCommand.builder()
                .id(findUserResult.id())
                .build());

        return ResponseEntity.ok("User online status updated successfully");

    }

}
