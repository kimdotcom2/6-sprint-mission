package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "code.repository.type", havingValue = "jcf")
public class JCFUserStatusRepository implements UserStatusRepository {

    private final Map<UUID,UserStatus> data;

    public JCFUserStatusRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public void save(UserStatus userStatus) {
        data.put(userStatus.getId(), userStatus);
    }

    @Override
    public void saveAll(Iterable<UserStatus> userStatus) {
        userStatus.forEach(this::save);
    }

    @Override
    public boolean existById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public boolean existByUserId(UUID userId) {
        return data.values().stream().anyMatch(userStatus -> userStatus.getUserId().equals(userId));
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        return data.values().stream()
                .filter(userStatus -> userStatus.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public List<UserStatus> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void deleteById(UUID id) {
        data.remove(id);
    }

    @Override
    public void deleteByUserId(UUID userId) {

        UserStatus userStatus = data.values().stream()
                .filter(status -> status.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        data.remove(userStatus.getId());

    }

    @Override
    public void deleteAllByIdIn(Iterable<UUID> idList) {
       idList.forEach(this::deleteById);
    }
}
