package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, UUID> {

    boolean existById(UUID id);

    boolean existByUserId(UUID userId);

    Optional<UserStatus> findById(UUID id);

    Optional<UserStatus> findByUserId(UUID userId);

    void deleteById(UUID id);

    void deleteByUserId(UUID userId);

    void deleteAllByIdIn(Collection<UUID> idList);

}
