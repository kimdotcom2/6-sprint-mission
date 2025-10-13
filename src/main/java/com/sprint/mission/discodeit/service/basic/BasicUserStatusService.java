package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatusDTO;
import com.sprint.mission.discodeit.entity.UserEntity;
import com.sprint.mission.discodeit.entity.UserStatusEntity;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import com.sprint.mission.discodeit.mapper.UserStatusEntityMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {

  private final UserStatusRepository userStatusRepository;
  private final UserRepository userRepository;
  private final UserStatusEntityMapper userStatusEntityMapper;

  @Transactional
  @Override
  public UserStatusDTO.UserStatus createUserStatus(UserStatusDTO.CreateUserStatusCommand request) {

    if (!userRepository.existsById(request.userId())) {
      throw new NoSuchDataBaseRecordException("No such user.");
    }

    if (existUserStatusByUserId(request.userId())) {
      throw new IllegalArgumentException("User status already exists.");
    }

    UserEntity userEntity = userRepository.findById(request.userId()).get();

    UserStatusEntity userStatusEntity = UserStatusEntity.builder()
        .user(userEntity)
        .build();

    return userStatusEntityMapper.entityToUserStatus(userStatusRepository.save(userStatusEntity));

  }

  @Override
  public boolean existUserStatusById(UUID id) {
    return userStatusRepository.existsById(id);
  }

  @Override
  public boolean existUserStatusByUserId(UUID userId) {
    return userStatusRepository.existsByUserId(userId);
  }

  @Override
  public Optional<UserStatusDTO.UserStatus> findUserStatusById(UUID id) {

    UserStatusEntity userStatusEntity = userStatusRepository.findById(id)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user status."));

    return Optional.ofNullable(userStatusEntityMapper.entityToUserStatus(userStatusEntity));

  }

  @Transactional(readOnly = true)
  @Override
  public Optional<UserStatusDTO.UserStatus> findUserStatusByUserId(UUID userId) {

    if (!userRepository.existsById(userId)) {
      throw new NoSuchDataBaseRecordException("No such user.");
    }

    UserStatusEntity userStatusEntity = userStatusRepository.findByUserId(userId)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user status."));

    return Optional.ofNullable(userStatusEntityMapper.entityToUserStatus(userStatusEntity));

  }

  @Override
  public List<UserStatusDTO.UserStatus> findAllUserStatus() {
    return userStatusRepository.findAll().stream()
        .map(userStatusEntityMapper::entityToUserStatus)
        .toList();

  }

  @Transactional
  @Override
  public UserStatusDTO.UserStatus updateUserStatus(UserStatusDTO.UpdateUserStatusCommand request) {

    UserStatusEntity userStatusEntity = userStatusRepository.findById(request.id())
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user status."));

    userStatusEntity.updateLastActiveAt(request.lastActiveAt());

    return userStatusEntityMapper.entityToUserStatus(userStatusRepository.save(userStatusEntity));

  }

  @Transactional
  @Override
  public void deleteUserStatusById(UUID id) {

    if (!userStatusRepository.existsById(id)) {
      throw new NoSuchDataBaseRecordException("No such user status.");
    }

    userStatusRepository.deleteById(id);

  }

  @Transactional
  @Override
  public void deleteUserStatusByUserId(UUID userId) {

    if (!userStatusRepository.existsById(userId)) {
      throw new NoSuchDataBaseRecordException("No such user status.");
    }

    userStatusRepository.deleteByUserId(userId);

  }

  @Transactional
  @Override
  public void deleteAllUserStatusByIdIn(List<UUID> uuidList) {

    uuidList.forEach(uuid -> {
      if (!userStatusRepository.existsById(uuid)) {
        throw new NoSuchDataBaseRecordException("No such user status.");
      }
    });

    userStatusRepository.deleteAllByIdIn(uuidList);

  }
}
