package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDTO;
import com.sprint.mission.discodeit.entity.BinaryContentEntity;
import com.sprint.mission.discodeit.entity.UserEntity;
import com.sprint.mission.discodeit.entity.UserStatusEntity;
import com.sprint.mission.discodeit.exception.AllReadyExistDataBaseRecordException;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import com.sprint.mission.discodeit.mapper.UserEntityMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import com.sprint.mission.discodeit.utils.SecurityUtil;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

  private final UserRepository userRepository;
  private final UserStatusRepository userStatusRepository;
  private final BinaryContentRepository binaryContentRepository;
  private final BinaryContentStorage binaryContentStorage;
  private final UserEntityMapper userEntityMapper;
  private final SecurityUtil securityUtil = new SecurityUtil();

  @Transactional
  @Override
  public UserDTO.User createUser(UserDTO.CreateUserCommand request) {

    if (userRepository.existsByEmailOrUsername(request.email(), request.username())) {
      throw new AllReadyExistDataBaseRecordException("User already exists.");
    }

    UserEntity userEntity = UserEntity.builder()
        .email(request.email())
        .password(securityUtil.hashPassword(request.password()))
        .username(request.username())
        .build();

    userEntity.updateUserStatus(UserStatusEntity.builder()
        .user(userEntity)
        .lastActiveAt(Instant.now())
        .build());

    if (request.profileImage() != null) {

      BinaryContentEntity binaryContentEntity = BinaryContentEntity.builder()
          .fileName(request.profileImage().fileName())
          .size((long) request.profileImage().data().length)
          .contentType(request.profileImage().contentType())
          .build();

      userEntity.updateProfile(binaryContentEntity);

    }

    UserDTO.User user = userEntityMapper.entityToUser(userRepository.save(userEntity));

    if (request.profileImage() != null) {
      binaryContentStorage.put(user.getProfileId().getId(), request.profileImage().data());
    }

    return  user;

  }

  @Override
  public boolean existUserById(UUID id) {
    return userRepository.existsById(id);
  }

  @Override
  public boolean existUserByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public boolean existUserByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<UserDTO.User> findUserById(UUID id) {

    UserEntity userEntity = userRepository.findById(id)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user."));

    UserStatusEntity userStatusEntity = userStatusRepository.findByUserId(id)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user status."));

    UserDTO.User user = userEntityMapper.entityToUser(userEntity);
    user.updateStatus(userStatusEntity.isOnline());

    return Optional.ofNullable(user);

  }

  @Override
  public Optional<UserDTO.User> findUserByEmail(String email) {

    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user."));

    UserStatusEntity userStatusEntity = userStatusRepository.findByUserId(userEntity.getId())
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user status."));

    UserDTO.User user = userEntityMapper.entityToUser(userEntity);
    user.updateStatus(userStatusEntity.isOnline());

    return Optional.ofNullable(user);

  }

  @Override
  public Optional<UserDTO.User> findUserByUsername(String username) {

    UserEntity userEntity = userRepository.findByUsername(username)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user."));

    UserStatusEntity userStatusEntity = userStatusRepository.findByUserId(userEntity.getId())
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user status."));

    UserDTO.User user = userEntityMapper.entityToUser(userEntity);
    user.updateStatus(userStatusEntity.isOnline());

    return Optional.ofNullable(user);

  }

  @Transactional(readOnly = true)
  @Override
  public List<UserDTO.User> findAllUsers() {

    return userRepository.findAll().stream()
        .map(userEntityMapper::entityToUser)
        .peek(user -> {
          UserStatusEntity userStatusEntity = userStatusRepository.findByUserId(user.getId())
              .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user status."));
          user.updateStatus(userStatusEntity.isOnline());
        })
        .toList();
  }

  @Transactional
  @Override
  public UserDTO.User updateUser(UserDTO.UpdateUserCommand request) {

    UserEntity updatedUserEntity = userRepository.findById(request.id())
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user."));

    if (userRepository.existsByEmailOrUsername(request.email(), request.username()) &&
        !updatedUserEntity.getId().equals(request.id())) {
      throw new AllReadyExistDataBaseRecordException("User already exists.");
    }

    if (!securityUtil.hashPassword(request.currentPassword())
        .equals(updatedUserEntity.getPassword())) {
      throw new IllegalArgumentException("Invalid password.");
    }

    updatedUserEntity.update(request.username(), request.email(),
        securityUtil.hashPassword(request.currentPassword()));

    if (request.isProfileImageUpdated()) {

      BinaryContentEntity binaryContentEntity = BinaryContentEntity.builder()
          .fileName(request.profileImage().fileName())
          .size((long) request.profileImage().data().length)
          .contentType(request.profileImage().contentType())
          .build();

      if (updatedUserEntity.getProfileId() != null) {
        binaryContentRepository.deleteById(updatedUserEntity.getProfileId().getId());
      }

      updatedUserEntity.updateProfile(binaryContentEntity);
      binaryContentStorage.put(binaryContentRepository.save(binaryContentEntity).getId(),
          request.profileImage().data());

    }

    return userEntityMapper.entityToUser(userRepository.save(updatedUserEntity));

  }

  @Override
  public void deleteUserById(UUID id) {

    UserEntity userEntity = userRepository.findById(id)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user."));

    binaryContentRepository.deleteById(userEntity.getProfileId().getId());
    userStatusRepository.deleteByUserId(userEntity.getId());
    userRepository.deleteById(id);

  }
}
