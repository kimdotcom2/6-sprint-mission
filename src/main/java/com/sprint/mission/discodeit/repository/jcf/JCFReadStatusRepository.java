package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFReadStatusRepository implements ReadStatusRepository {
    @Override
    public void save(ReadStatus readStatus) {

    }

    @Override
    public void saveAll(Iterable<ReadStatus> readStatus) {

    }

    @Override
    public boolean existById(UUID id) {
        return false;
    }

    @Override
    public boolean existByUserIdAndChannelId(UUID userId, UUID channelId) {
        return false;
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId) {
        return Optional.empty();
    }

    @Override
    public List<ReadStatus> findByUserId(UUID userId) {
        return List.of();
    }

    @Override
    public List<ReadStatus> findByChannelId(UUID channelId) {
        return List.of();
    }

    @Override
    public List<ReadStatus> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void deleteByUserIdAndChannelId(UUID userId, UUID channelId) {

    }

    @Override
    public void deleteByUserId(UUID userId) {

    }

    @Override
    public void deleteByChannelId(UUID channelId) {

    }

    @Override
    public void deleteAll(Iterable<UUID> readStatus) {

    }
}
