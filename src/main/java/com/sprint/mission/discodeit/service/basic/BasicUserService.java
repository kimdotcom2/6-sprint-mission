package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.exception.AllReadyExistDataException;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
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

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final SecurityUtil securityUtil = new SecurityUtil();

    @Override
    public void createUser(UserDTO.CreateUserCommand request) {

        if (userRepository.existByEmailOrNickname(request.email(), request.nickname())) {
            throw new AllReadyExistDataException("User already exists.");
        }

        User user = new User.Builder()
                .email(request.email())
                .password(securityUtil.hashPassword(request.password()))
                .nickname(request.nickname())
                .description(request.description())
                .build();

        userStatusRepository.save(new UserStatus(user.getId()));

        if (request.profileImage() != null) {

            BinaryContent binaryContent = BinaryContent.builder()
                    .fileName(user.getUsername() + "_profileImage")
                    .size((long) request.profileImage().data().length)
                    .data(request.profileImage().data())
                    .fileType(request.profileImage().fileType())
                    .build();

            binaryContentRepository.save(binaryContent);
            user.updateProfileImageId(binaryContent.getId());

        }

        userRepository.save(user);

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
    public boolean existUserByNickname(String nickname) {
        return userRepository.existByNickname(nickname);
    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        UserStatus userStatus = userStatusRepository.findByUserId(id)
                .orElseThrow(() -> new NoSuchDataException("No such user status."));

        return Optional.ofNullable(UserDTO.FindUserResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getUsername())
                .description(user.getDescription())
                .profileImageId(user.getProfileImageId())
                .isOnline(userStatus.isLogin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build());

    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchDataException("No such user status."));

        return Optional.ofNullable(UserDTO.FindUserResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getUsername())
                .description(user.getDescription())
                .profileImageId(user.getProfileImageId())
                .isOnline(userStatus.isLogin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build());

    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserByNickname(String nickname) {

        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchDataException("No such user status."));

        return Optional.ofNullable(UserDTO.FindUserResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getUsername())
                .description(user.getDescription())
                .profileImageId(user.getProfileImageId())
                .isOnline(userStatus.isLogin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build());

    }

    @Override
    public List<UserDTO.FindUserResult> findAllUsers() {

        return userRepository.findAll().stream()
                .map(user -> UserDTO.FindUserResult.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .nickname(user.getUsername())
                        .description(user.getDescription())
                        .profileImageId(user.getProfileImageId())
                        .isOnline(userStatusRepository.findByUserId(user.getId())
                                .orElseThrow(() -> new NoSuchDataException("No such user status.")).isLogin())
                        .createdAt(user.getCreatedAt())
                        .updatedAt(user.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public void updateUser(UserDTO.UpdateUserCommand request) {

        User updatedUser = userRepository.findById(request.id())
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        if (userRepository.existByEmailOrNickname(request.email(), request.nickname()) &&
                !updatedUser.getId().equals(request.id())) {
            throw new AllReadyExistDataException("User already exists.");
        }

        if (!securityUtil.hashPassword(request.currentPassword()).equals(updatedUser.getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        updatedUser.update(request.nickname(), request.email(), securityUtil.hashPassword(request.currentPassword()), request.description());

        if (request.isProfileImageUpdated()) {

            BinaryContent binaryContent = BinaryContent.builder()
                    .fileName(request.profileImage().fileName())
                    .size((long) request.profileImage().data().length)
                    .data(request.profileImage().data())
                    .fileType(request.profileImage().fileType())
                    .build();

            if (updatedUser.getProfileImageId() != null) {
              binaryContentRepository.deleteById(updatedUser.getProfileImageId());
            }

            binaryContentRepository.save(binaryContent);
            updatedUser.updateProfileImageId(binaryContent.getId());

        }

        userRepository.save(updatedUser);

    }

    @Override
    public void deleteUserById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        binaryContentRepository.deleteById(user.getProfileImageId());
        userStatusRepository.deleteByUserId(user.getId());
        userRepository.deleteById(id);

    }
}
