package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.utils.SecurityUtil;

import java.util.*;

public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil = new SecurityUtil();

    public BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {

        if (userRepository.existById(user.getId())) {
            throw new IllegalArgumentException("User already exists.");
        }

        if (user.getPassword().isBlank() || user.getEmail().isBlank() || user.getNickname().isBlank()) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        if (userRepository.existByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        user.setPassword(securityUtil.hashPassword(user.getPassword()));

        userRepository.save(user);

    }

    @Override
    public boolean existUserById(UUID id) {
        return userRepository.existById(id);
    }

    @Override
    public boolean existUserByEmail(String email) {
        return userRepository.existByEmail(email);
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
    public void updateUser(UUID id, String nickname, String email, String currentPassword, String newPassword, String description) {

        if (email.isBlank() || newPassword.isBlank() || nickname.isBlank()) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        User updatedUser = findUserById(id).orElseThrow(() -> new IllegalArgumentException("No such user."));

        if (userRepository.existByEmail(email) && !updatedUser.getId().equals(id)) {
            throw new IllegalArgumentException("Email already exists.");
        }

        if (!securityUtil.hashPassword(currentPassword).equals(updatedUser.getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        updatedUser.update(nickname, email, securityUtil.hashPassword(currentPassword), description);

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
