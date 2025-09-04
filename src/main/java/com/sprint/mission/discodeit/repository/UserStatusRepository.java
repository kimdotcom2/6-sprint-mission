package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
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

    void deleteAll(Iterable<UUID> userStatus);

}
