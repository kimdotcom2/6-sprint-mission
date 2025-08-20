package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserCrudService implements UserService {

    private final Map<UUID, User> data;

    public JCFUserCrudService() {
        data = new HashMap<>();
    }

    @Override
    public void create(User user) {

        if (data.containsKey(user.getId())) {
            throw new IllegalArgumentException("User already exists.");
        }

        data.put(user.getId(), user);

    }

    @Override
    public boolean existById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public Optional<User> findUserById(UUID id) {

        if (!data.containsKey(id)) {
            return Optional.empty();
        }

        User user = data.get(id);

        //System.out.print("ID: " + user.getId() + " nickname: " + user.getNickname() + " email: " + user.getEmail());
        //System.out.println(user.toString());
        return Optional.of(user);

    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        return data.entrySet().stream()
                .filter(entry -> entry.getValue().getEmail().equals(email))
                .findFirst().map(Map.Entry::getValue);

    }

    @Override
    public List<User> findAllUsers() {

        System.out.println("User List:");

        /*data.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getValue().toString());
        });*/
        return List.of(data.values().toArray(new User[0]));

    }

    @Override
    public void update(UUID id, String nickname, String email, String password, String description) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        data.get(id).update(nickname, email, password, description);

    }

    @Override
    public void deleteById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        data.remove(id);

    }
}
