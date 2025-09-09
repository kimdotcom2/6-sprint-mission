package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "code.repository.type", havingValue = "jcf")
public class JCFUserRepository implements UserRepository {

    private final Map<UUID, User> data;

    public JCFUserRepository() {
        data = new HashMap<>();
    }

    @Override
    public void save(User user) {
        data.put(user.getId(), user);
    }

    @Override
    public void saveAll(Iterable<User> users) {
        users.forEach(user -> data.put(user.getId(), user));
    }

    @Override
    public boolean existById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public boolean existByEmail(String email) {
        return data.values().stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean existByNickname(String nickname) {
        return data.values().stream().anyMatch(user -> user.getNickname().equals(nickname));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return data.entrySet().stream()
                .filter(entry -> entry.getValue().getEmail().equals(email))
                .findFirst().map(Map.Entry::getValue);
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return data.entrySet().stream()
                .filter(entry -> entry.getValue().getNickname().equals(nickname))
                .findFirst().map(Map.Entry::getValue);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void deleteById(UUID id) {
        data.remove(id);
    }
}
