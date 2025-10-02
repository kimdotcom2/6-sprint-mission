package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.BinaryContentEntity;
import com.sprint.mission.discodeit.entity.UserEntity;
import com.sprint.mission.discodeit.entity.UserStatusEntity;
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

        if (userRepository.existByEmailOrNickname(request.email(), request.username())) {
            throw new AllReadyExistDataException("User already exists.");
        }

        UserEntity userEntity = new UserEntity.Builder()
                .email(request.email())
                .password(securityUtil.hashPassword(request.password()))
                .nickname(request.username())
                .description(request.description())
                .build();

        userStatusRepository.save(new UserStatusEntity(userEntity.getId()));

        if (request.profileImage() != null) {

            BinaryContentEntity binaryContentEntity = BinaryContentEntity.builder()
                    .fileName(userEntity.getUsername() + "_profileImage")
                    .size((long) request.profileImage().data().length)
                    .data(request.profileImage().data())
                    .fileType(request.profileImage().contentType())
                    .build();

            binaryContentRepository.save(binaryContentEntity);
            userEntity.updateProfileImageId(binaryContentEntity.getId());

        }

        userRepository.save(userEntity);

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

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        UserStatusEntity userStatusEntity = userStatusRepository.findByUserId(id)
                .orElseThrow(() -> new NoSuchDataException("No such user status."));

        return Optional.ofNullable(UserDTO.FindUserResult.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .nickname(userEntity.getUsername())
                .description(userEntity.getDescription())
                .profileImageId(userEntity.getProfileImageId())
                .isOnline(userStatusEntity.isLogin())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build());

    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserByEmail(String email) {

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        UserStatusEntity userStatusEntity = userStatusRepository.findByUserId(userEntity.getId())
                .orElseThrow(() -> new NoSuchDataException("No such user status."));

        return Optional.ofNullable(UserDTO.FindUserResult.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .nickname(userEntity.getUsername())
                .description(userEntity.getDescription())
                .profileImageId(userEntity.getProfileImageId())
                .isOnline(userStatusEntity.isLogin())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build());

    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserByNickname(String nickname) {

        UserEntity userEntity = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        UserStatusEntity userStatusEntity = userStatusRepository.findByUserId(userEntity.getId())
                .orElseThrow(() -> new NoSuchDataException("No such user status."));

        return Optional.ofNullable(UserDTO.FindUserResult.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .nickname(userEntity.getUsername())
                .description(userEntity.getDescription())
                .profileImageId(userEntity.getProfileImageId())
                .isOnline(userStatusEntity.isLogin())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
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

        UserEntity updatedUserEntity = userRepository.findById(request.id())
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        if (userRepository.existByEmailOrNickname(request.email(), request.username()) &&
                !updatedUserEntity.getId().equals(request.id())) {
            throw new AllReadyExistDataException("User already exists.");
        }

        if (!securityUtil.hashPassword(request.currentPassword()).equals(updatedUserEntity.getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        updatedUserEntity.update(request.username(), request.email(), securityUtil.hashPassword(request.currentPassword()), request.description());

        if (request.isProfileImageUpdated()) {

            BinaryContentEntity binaryContentEntity = BinaryContentEntity.builder()
                    .fileName(request.profileImage().fileName())
                    .size((long) request.profileImage().data().length)
                    .data(request.profileImage().data())
                    .fileType(request.profileImage().contentType())
                    .build();

            if (updatedUserEntity.getProfileImageId() != null) {
              binaryContentRepository.deleteById(updatedUserEntity.getProfileImageId());
            }

            binaryContentRepository.save(binaryContentEntity);
            updatedUserEntity.updateProfileImageId(binaryContentEntity.getId());

        }

        userRepository.save(updatedUserEntity);

    }

    @Override
    public void deleteUserById(UUID id) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("No such user."));

        binaryContentRepository.deleteById(userEntity.getProfileImageId());
        userStatusRepository.deleteByUserId(userEntity.getId());
        userRepository.deleteById(id);

    }
}
