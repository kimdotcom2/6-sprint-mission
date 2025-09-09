package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "code.repository.type", havingValue = "jcf")
public class JCFReadStatusRepository implements ReadStatusRepository {

    private final Map<UUID, ReadStatus> data;

    public JCFReadStatusRepository() {
        data = new HashMap<>();
    }

    @Override
    public void save(ReadStatus readStatus) {
        data.put(readStatus.getId(), readStatus);
    }

    @Override
    public void saveAll(Iterable<ReadStatus> readStatus) {
        readStatus.forEach(this::save);
    }

    @Override
    public boolean existById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public boolean existByUserIdAndChannelId(UUID userId, UUID channelId) {

        return data.values().stream()
            .anyMatch(readStatus -> readStatus.getUserId().equals(userId)
                    && readStatus.getChannelId().equals(channelId));

    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId) {
        return data.values().stream()
            .filter(readStatus -> readStatus.getUserId().equals(userId)
                    && readStatus.getChannelId().equals(channelId))
            .findFirst();
    }

    @Override
    public List<ReadStatus> findByUserId(UUID userId) {
        return data.values().stream()
                .filter(readStatus -> readStatus.getUserId().equals(userId))
                .toList();
    }

    @Override
    public List<ReadStatus> findByChannelId(UUID channelId) {
        return data.values().stream()
                .filter(readStatus -> readStatus.getChannelId().equals(channelId))
                .toList();
    }

    @Override
    public List<ReadStatus> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void deleteById(UUID id) {
        data.remove(id);
    }

    @Override
    public void deleteByUserIdAndChannelId(UUID userId, UUID channelId) {

        ReadStatus readStatus = data.values().stream()
                .filter(status -> status.getUserId().equals(userId)
                        && status.getChannelId().equals(channelId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        data.remove(readStatus.getId());

    }

    @Override
    public void deleteByUserId(UUID userId) {

        List<ReadStatus> readStatus = data.values().stream()
                .filter(status -> status.getUserId().equals(userId))
                .toList();

        readStatus.forEach(status -> data.remove(status.getId()));

    }

    @Override
    public void deleteByChannelId(UUID channelId) {

        List<ReadStatus> readStatus = data.values().stream()
                .filter(status -> status.getChannelId().equals(channelId))
                .toList();

        readStatus.forEach(status -> data.remove(status.getId()));

    }

    @Override
    public void deleteAllByIdIn(Iterable<UUID> idList) {

        idList.forEach(this::deleteById);

    }
}
