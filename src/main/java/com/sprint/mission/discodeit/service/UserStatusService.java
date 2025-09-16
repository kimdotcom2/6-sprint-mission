package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserStatusDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusService {

    void createUserStatus(UserStatusDTO.CreateReadStatusCommand request);

    boolean existUserStatusById(UUID id);

    boolean existUserStatusByUserId(UUID userId);

    Optional<UserStatusDTO.FindReadStatusResult> findUserStatusById(UUID id);

    Optional<UserStatusDTO.FindReadStatusResult> findUserStatusByUserId(UUID userId);

    List<UserStatusDTO.FindReadStatusResult> findAllUserStatus();

    void updateUserStatus(UserStatusDTO.UpdateReadStatusCommand request);

    void deleteUserStatusById(UUID id);

    void deleteUserStatusByUserId(UUID userId);

    void deleteAllUserStatusByIdIn(List<UUID> idList);

}
