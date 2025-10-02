package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatusDTO;
import com.sprint.mission.discodeit.entity.UserStatusEntity;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public void createUserStatus(UserStatusDTO.CreateUserStatusCommand request) {

        if (!userRepository.existById(request.userId())) {
            throw new IllegalArgumentException("No such user.");
        }

        if (existUserStatusByUserId(request.userId())) {
            throw new IllegalArgumentException("User status already exists.");
        }

        UserStatusEntity userStatusEntity = new UserStatusEntity.Builder()
                .userId(request.userId())
                .build();

        userStatusRepository.save(userStatusEntity);

    }

    @Override
    public boolean existUserStatusById(UUID id) {
        return userStatusRepository.existById(id);
    }

    @Override
    public boolean existUserStatusByUserId(UUID userId) {
        return userStatusRepository.existByUserId(userId);
    }

    @Override
    public Optional<UserStatusDTO.FindUserStatusResult> findUserStatusById(UUID id) {

        UserStatusEntity userStatusEntity = userStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such user status."));

        return Optional.ofNullable(UserStatusDTO.FindUserStatusResult.builder()
                .id(userStatusEntity.getId())
                .userId(userStatusEntity.getUserId())
                .lastActiveTimestamp(userStatusEntity.getLastActiveTimestamp())
                .createdAt(userStatusEntity.getCreatedAt())
                .updatedAt(userStatusEntity.getUpdatedAt())
                .build());

    }

    @Override
    public Optional<UserStatusDTO.FindUserStatusResult> findUserStatusByUserId(UUID userId) {

        if (!userRepository.existById(userId)) {
            throw new IllegalArgumentException("No such user.");
        }

        UserStatusEntity userStatusEntity = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user status."));

        return Optional.ofNullable(UserStatusDTO.FindUserStatusResult.builder()
                .id(userStatusEntity.getId())
                .userId(userStatusEntity.getUserId())
                .lastActiveTimestamp(userStatusEntity.getLastActiveTimestamp())
                .createdAt(userStatusEntity.getCreatedAt())
                .updatedAt(userStatusEntity.getUpdatedAt())
                .build());

    }

    @Override
    public List<UserStatusDTO.FindUserStatusResult> findAllUserStatus() {
        return userStatusRepository.findAll().stream()
                .map(userStatusEntity -> UserStatusDTO.FindUserStatusResult.builder()
                        .id(userStatusEntity.getId())
                        .userId(userStatusEntity.getUserId())
                        .lastActiveTimestamp(userStatusEntity.getLastActiveTimestamp())
                        .createdAt(userStatusEntity.getCreatedAt())
                        .updatedAt(userStatusEntity.getUpdatedAt())
                        .build())
                .toList();

    }

    @Override
    public void updateUserStatus(UserStatusDTO.UpdateUserStatusCommand request) {

        UserStatusEntity userStatusEntity = userStatusRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("No such user status."));

        userStatusEntity.updateLastActiveTimestamp();

        userStatusRepository.save(userStatusEntity);
    }

    @Override
    public void deleteUserStatusById(UUID id) {

        if (!userStatusRepository.existById(id)) {
            throw new IllegalArgumentException("No such user status.");
        }

        userStatusRepository.deleteById(id);

    }

    @Override
    public void deleteUserStatusByUserId(UUID userId) {

        if (!userStatusRepository.existById(userId)) {
            throw new IllegalArgumentException("No such user status.");
        }

        userStatusRepository.deleteByUserId(userId);

    }

    @Override
    public void deleteAllUserStatusByIdIn(List<UUID> uuidList) {

        uuidList.forEach(uuid -> {
            if (!userStatusRepository.existById(uuid)) {
                throw new IllegalArgumentException("No such user status.");
            }
        });

        userStatusRepository.deleteAllByIdIn(uuidList);

    }
}
