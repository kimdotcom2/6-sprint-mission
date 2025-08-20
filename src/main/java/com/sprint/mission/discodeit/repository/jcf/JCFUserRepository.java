package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.Map;
import java.util.UUID;

public class JCFUserRepository implements UserRepository {

    private final Map<UUID, User> data;

    public JCFUserRepository(Map<UUID, User> data) {
        this.data = data;
    }

    @Override
    public void save(User user) {
        data.put(user.getId(), user);
    }

    @Override
    public void saveAll(Iterable<User> users) {
        users.forEach(user -> data.put(user.getId(), user));
    }
}
