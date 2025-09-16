package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatusDTO;
import com.sprint.mission.discodeit.entity.UserStatus;
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

        UserStatus userStatus = new UserStatus.Builder()
                .userId(request.userId())
                .build();

        userStatusRepository.save(userStatus);

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

        UserStatus userStatus = userStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such user status."));

        return Optional.ofNullable(UserStatusDTO.FindUserStatusResult.builder()
                .id(userStatus.getId())
                .userId(userStatus.getUserId())
                .lastActiveTimestamp(userStatus.getLastActiveTimestamp())
                .createdAt(userStatus.getCreatedAt())
                .updatedAt(userStatus.getUpdatedAt())
                .build());

    }

    @Override
    public Optional<UserStatusDTO.FindUserStatusResult> findUserStatusByUserId(UUID userId) {

        if (!userRepository.existById(userId)) {
            throw new IllegalArgumentException("No such user.");
        }

        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No such user status."));

        return Optional.ofNullable(UserStatusDTO.FindUserStatusResult.builder()
                .id(userStatus.getId())
                .userId(userStatus.getUserId())
                .lastActiveTimestamp(userStatus.getLastActiveTimestamp())
                .createdAt(userStatus.getCreatedAt())
                .updatedAt(userStatus.getUpdatedAt())
                .build());

    }

    @Override
    public List<UserStatusDTO.FindUserStatusResult> findAllUserStatus() {
        return userStatusRepository.findAll().stream()
                .map(userStatus -> UserStatusDTO.FindUserStatusResult.builder()
                        .id(userStatus.getId())
                        .userId(userStatus.getUserId())
                        .lastActiveTimestamp(userStatus.getLastActiveTimestamp())
                        .createdAt(userStatus.getCreatedAt())
                        .updatedAt(userStatus.getUpdatedAt())
                        .build())
                .toList();

    }

    @Override
    public void updateUserStatus(UserStatusDTO.UpdateUserStatusCommand request) {

        UserStatus userStatus = userStatusRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("No such user status."));

        userStatus.updateLastActiveTimestamp();

        userStatusRepository.save(userStatus);
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
