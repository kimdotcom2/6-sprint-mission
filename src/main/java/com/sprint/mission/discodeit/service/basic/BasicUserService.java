package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final Map<UUID, User> data;

    public BasicUserService(UserRepository userRepository) {
        data = new TreeMap<>();
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {

        if (data.containsKey(user.getId())) {
            throw new IllegalArgumentException("User already exists.");
        }

        userRepository.save(user);

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
        return List.of();
    }

    @Override
    public void updateUser(UUID id, String nickname, String email, String password, String description) {

    }

    @Override
    public void deleteUserById(UUID id) {

    }
}
