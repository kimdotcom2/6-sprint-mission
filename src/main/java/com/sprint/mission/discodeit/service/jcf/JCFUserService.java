package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.dto.DiscordDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.utils.SecurityUtil;
import com.sprint.mission.discodeit.utils.ValidationUtil;

import java.util.*;

public class JCFUserService implements UserService {

    private final Map<UUID, User> data;
    private final SecurityUtil securityUtil = new SecurityUtil();
    private final ValidationUtil validationUtil = new ValidationUtil();

    public JCFUserService() {
        data = new TreeMap<>();
    }

    @Override
    public void createUser(User user) {

        if (data.containsKey(user.getId())) {
            throw new IllegalArgumentException("User already exists.");
        }

        if (!validationUtil.isPasswordValid(user.getPassword()) || !validationUtil.isPasswordValid(user.getPassword()) || user.getNickname().isBlank()) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        if (existUserByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        user.setPassword(securityUtil.hashPassword(user.getPassword()));
        data.put(user.getId(), user);

    }

    @Override
    public boolean existUserById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public boolean existUserByEmail(String email) {

        for (User existingUser : data.values()) {
            if (existingUser.getEmail().equals(email)) {
                return true;
            }
        }

        return false;

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

        if (!existUserByEmail(email)) {
            return Optional.empty();
        }

        return data.entrySet().stream()
                .filter(entry -> entry.getValue().getEmail().equals(email))
                .findFirst().map(Map.Entry::getValue);

    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateUser(DiscordDTO.UpdateUserRequest request) {

        if (!validationUtil.isEmailValid(request.email()) ||
                request.nickname().isBlank() ||
                !validationUtil.isPasswordValid(request.newPassword()) ||
                !validationUtil.isPasswordValid(request.currentPassword())) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        if (!data.containsKey(request.id())) {
            throw new IllegalArgumentException("No such user.");
        }

        if (existUserByEmail(request.email()) && !data.get(request.id()).getEmail().equals(request.email())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        if (!securityUtil.hashPassword(request.currentPassword()).equals(data.get(request.id()).getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        data.get(request.id()).update(request.nickname(), request.email(), securityUtil.hashPassword(request.newPassword()), request.description());

    }

    @Override
    public void deleteUserById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("No such user.");
        }

        data.remove(id);

    }
}
