package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserStatusDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusService {

    void createUserStatus(UserStatusDTO.CreateUserStatusCommand request);

    boolean existUserStatusById(UUID id);

    boolean existUserStatusByUserId(UUID userId);

    Optional<UserStatusDTO.FindUserStatusResult> findUserStatusById(UUID id);

    Optional<UserStatusDTO.FindUserStatusResult> findUserStatusByUserId(UUID userId);

    List<UserStatusDTO.FindUserStatusResult> findAllUserStatus();

    void updateUserStatus(UserStatusDTO.UpdateUserStatusCommand request);

    void deleteUserStatusById(UUID id);

    void deleteUserStatusByUserId(UUID userId);

    void deleteAllUserStatusByIdIn(List<UUID> idList);

}
