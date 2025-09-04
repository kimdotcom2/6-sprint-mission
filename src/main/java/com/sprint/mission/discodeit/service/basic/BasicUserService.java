package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.component.Validator;
import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("basicUserService")
@RequiredArgsConstructor
public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final SecurityUtil securityUtil = new SecurityUtil();
    private final Validator validator;

    @Override
    public void createUser(User user) {

        if (userRepository.existById(user.getId())) {
            throw new IllegalArgumentException("User already exists.");
        }

        if (!validator.isPasswordValid(user.getPassword()) || !validator.isEmailValid(user.getEmail()) || user.getNickname().isBlank()) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        if (userRepository.existByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        user.setPassword(securityUtil.hashPassword(user.getPassword()));

        userRepository.save(user);

        userStatusRepository.save(new UserStatus(user.getId()));

    }

    @Override
    public boolean existUserById(UUID id) {
        return userRepository.existById(id);
    }

    @Override
    public boolean existUserByEmail(String email) {
        return userRepository.existByEmail(email);
    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such user."));

        UserStatus userStatus = userStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such user status."));

        return Optional.ofNullable(UserDTO.FindUserResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .profileImageId(user.getProfileImageId())
                .isOnline(userStatus.isLogin())
                .build());

    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No such user."));

        UserStatus userStatus = userStatusRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("No such user status."));

        return Optional.ofNullable(UserDTO.FindUserResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .profileImageId(user.getProfileImageId())
                .isOnline(userStatus.isLogin())
                .build());

    }

    @Override
    public List<UserDTO.FindUserResult> findAllUsers() {

        return userRepository.findAll().stream()
                .map(user -> UserDTO.FindUserResult.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .nickname(user.getNickname())
                        .description(user.getDescription())
                        .profileImageId(user.getProfileImageId())
                        .isOnline(userStatusRepository.findById(user.getId())
                                .orElseThrow(() -> new IllegalArgumentException("No such user status.")).isLogin())
                        .build())
                .toList();
    }

    @Override
    public void updateUser(UserDTO.UpdateUserRequest request) {

        if (!validator.isEmailValid(request.email()) ||
                !validator.isPasswordValid(request.newPassword()) ||
                !validator.isPasswordValid(request.currentPassword()) ||
                request.nickname().isBlank()) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        User updatedUser = userRepository.findById(request.id()).orElseThrow(() -> new IllegalArgumentException("No such user."));

        if (userRepository.existByEmail(request.email()) && !updatedUser.getId().equals(request.id())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        if (!securityUtil.hashPassword(request.currentPassword()).equals(updatedUser.getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        updatedUser.update(request.nickname(), request.email(), securityUtil.hashPassword(request.currentPassword()), request.description());

        userRepository.save(updatedUser);

    }

    @Override
    public void deleteUserById(UUID id) {

        if (!userRepository.existById(id)) {
            throw new IllegalArgumentException("No such user.");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such user."));

        binaryContentRepository.deleteById(user.getProfileImageId());
        userStatusRepository.deleteById(user.getId());
        userRepository.deleteById(id);

    }
}
