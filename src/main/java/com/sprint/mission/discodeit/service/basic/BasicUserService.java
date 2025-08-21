package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class BasicUserService implements UserService {

    private final UserRepository userRepository;

    public BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {

        if (userRepository.existById(user.getId())) {
            throw new IllegalArgumentException("User already exists.");
        }

        userRepository.save(user);

    }

    @Override
    public boolean existUserById(UUID id) {
        return userRepository.existById(id);
    }

    @Override
    public Optional<User> findUserById(UUID id) {

        if (!userRepository.existById(id)) {
            throw new IllegalArgumentException("No such user.");
        }

        return userRepository.findById(id);

    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        if (!userRepository.existByEmail(email)) {
            throw new IllegalArgumentException("No such user.");
        }

        return userRepository.findByEmail(email);

    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(UUID id, String nickname, String email, String password, String description) {

        if (!userRepository.existById(id)) {
            throw new IllegalArgumentException("No such user.");
        }

        if (email.isBlank() || password.isBlank() || nickname.isBlank()) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        if (findUserByEmail(email).isPresent() && !findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such user.")).getId().equals(id)) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User updatedUser = findUserById(id).orElseThrow(() -> new IllegalArgumentException("No such user."));
        updatedUser.update(nickname, email, password, description);

        userRepository.save(updatedUser);

    }

    @Override
    public void deleteUserById(UUID id) {

        if (!userRepository.existById(id)) {
            throw new IllegalArgumentException("No such user.");
        }

        userRepository.deleteById(id);

    }
}
