package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserStatusDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusService {

    void createUserStatus(UserStatusDTO.CreateReadStatusRequest request);

    boolean existUserStatusById(UUID id);

    boolean existUserStatusByUserId(UUID userId);

    Optional<UserStatusDTO.FindReadStatusRequest> findUserStatusById(UUID id);

    Optional<UserStatusDTO.FindReadStatusRequest> findUserStatusByUserId(UUID userId);

    List<UserStatusDTO.FindReadStatusRequest> findAllUserStatus();

    void updateUserStatus(UserStatusDTO.UpdateReadStatusRequest request);

    void deleteUserStatusById(UUID id);

    void deleteUserStatusByUserId(UUID userId);

    void deleteAllUserStatusByIdIn(List<UUID> idList);

}
