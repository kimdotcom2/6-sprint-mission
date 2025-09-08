package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadStatusRepository {

    void save(ReadStatus readStatus);

    void saveAll(Iterable<ReadStatus> readStatus);

    boolean existById(UUID id);

    boolean existByUserIdAndChannelId(UUID userId, UUID channelId);

    Optional<ReadStatus> findById(UUID id);

    Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId);

    List<ReadStatus> findByUserId(UUID userId);

    List<ReadStatus> findByChannelId(UUID channelId);

    List<ReadStatus> findAll();

    void deleteById(UUID id);

    void deleteByUserIdAndChannelId(UUID userId, UUID channelId);

    void deleteByUserId(UUID userId);

    void deleteByChannelId(UUID channelId);

    void deleteAllByIdIn(Iterable<UUID> idList);

}
