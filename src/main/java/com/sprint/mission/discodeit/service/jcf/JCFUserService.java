package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {

    private final Map<UUID, User> data;

    public JCFUserService() {
        data = new TreeMap<>();
    }

    @Override
    public void createUser(User user) {

        if (data.containsKey(user.getId())) {
            throw new IllegalArgumentException("User already exists.");
        }

        data.put(user.getId(), user);

    }

    @Override
    public boolean existUserById(UUID id) {
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
        return new ArrayList<>(data.values());

    }

    @Override
    public void updateUser(UUID id, String nickname, String email, String password, String description) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("No such user.");
        }

        data.get(id).update(nickname, email, password, description);

    }

    @Override
    public void deleteUserById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("No such user.");
        }

        data.remove(id);

    }
}
