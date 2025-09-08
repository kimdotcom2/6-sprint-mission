package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStatusRepository {

    void save(UserStatus userStatus);

    void saveAll(Iterable<UserStatus> userStatus);

    boolean existById(UUID id);

    boolean existByUserId(UUID userId);

    Optional<UserStatus> findById(UUID id);

    Optional<UserStatus> findByUserId(UUID userId);

    List<UserStatus> findAll();

    void deleteById(UUID id);

    void deleteByUserId(UUID userId);

    void deleteAllByIdIn(Iterable<UUID> idList);

}
