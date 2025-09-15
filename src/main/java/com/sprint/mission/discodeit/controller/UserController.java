package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.dto.api.UserApiDTO;
import com.sprint.mission.discodeit.service.AuthService;
import com.sprint.mission.discodeit.service.UserService;
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

    @RequestMapping(name = "/api/user/signup", method = RequestMethod.POST)
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

    @RequestMapping(name = "/api/user/update", method = RequestMethod.PUT)
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

    @RequestMapping(name = "/api/user/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@RequestBody UserApiDTO.DeleteUserRequest deleteUserRequest) {

        userService.deleteUserById(UUID.fromString(deleteUserRequest.id()));

        return ResponseEntity.ok("User deleted successfully");

    }

    @RequestMapping(name = "/api/user/findAll", method = RequestMethod.GET)
    public List<UserApiDTO.FindUserResponse> findAllUsers() {

        return userService.findAllUsers().stream()
                .map(user -> UserApiDTO.FindUserResponse.builder()
                        .id(user.id())
                        .nickname(user.nickname())
                        .email(user.email())
                        .description(user.description())
                        .profileImageId(user.profileImageId())
                        .build())

                .toList();

    }

    @RequestMapping(name = "api/user/check-is-online", method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkIsOnline(@RequestParam String id) {

        UserDTO.FindUserResult user = userService.findUserById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("No such user."));

        return ResponseEntity.ok(user.isOnline());

    }

}
