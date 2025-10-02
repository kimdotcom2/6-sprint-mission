package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, UUID> {

    boolean existById(UUID id);

    boolean existByUserIdAndChannelId(UUID userId, UUID channelId);

    Optional<ReadStatus> findById(UUID id);

    Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId);

    List<ReadStatus> findByUserId(UUID userId);

    List<ReadStatus> findByChannelId(UUID channelId);

    void deleteById(UUID id);

    void deleteByUserIdAndChannelId(UUID userId, UUID channelId);

    void deleteByUserId(UUID userId);

    void deleteByChannelId(UUID channelId);

    void deleteAllByIdIn(Collection<UUID> idList);

}
