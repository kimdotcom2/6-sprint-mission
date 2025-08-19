package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.JCFUserService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JCFUserCrudService implements JCFUserService {

    private final Map<UUID, User> data;

    public JCFUserCrudService() {
        data = new HashMap<>();
    }

    @Override
    public void create(User user) {

        data.put(user.getId(), user);

    }

    @Override
    public boolean existById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public void readById(UUID id) {

        if (!data.containsKey(id)) {

            System.out.println("No such user.");
            return;

        }

        User user = data.get(id);

        System.out.print("ID: " + user.getId() + " nickname: " + user.getNickname() + " email: " + user.getEmail());
        System.out.println(user.toString());

    }

    @Override
    public void readAll() {

        System.out.println("User List:");

        data.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getValue().toString());
        });

    }

    @Override
    public void update(UUID id, String nickname, String email, String password, String description) {

        if (!data.containsKey(id)) {

            System.out.println("No such user.");
            return;

        }

        data.get(id).update(nickname, email, password, description);

    }

    @Override
    public void deleteById(UUID id) {

        if (!data.containsKey(id)) {

            System.out.println("No such user.");
            return;

        }

        data.remove(id);

    }
}
