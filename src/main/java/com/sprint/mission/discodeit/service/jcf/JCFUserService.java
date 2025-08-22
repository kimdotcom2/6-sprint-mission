package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.utils.SecurityUtil;

import java.util.*;

public class JCFUserService implements UserService {

    private final Map<UUID, User> data;
    private final SecurityUtil securityUtil = new SecurityUtil();

    public JCFUserService() {
        data = new TreeMap<>();
    }

    @Override
    public void createUser(User user) {

        if (data.containsKey(user.getId())) {
            throw new IllegalArgumentException("User already exists.");
        }

        if (user.getPassword().isBlank() || user.getEmail().isBlank() || user.getNickname().isBlank()) {
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

        //System.out.print("ID: " + user.getId() + " nickname: " + user.getNickname() + " email: " + user.getEmail());
        //System.out.println(user.toString());
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

        //System.out.println("User List:");

        /*data.entrySet().stream().forEach(entry -> {
            System.out.println(entry.getValue().toString());
        });
        return data.entrySet().stream()
                .map(Map.Entry::getValue)
                .sorted(Comparator.comparing(User::getCreatedAt))
                .toList();*/
        return new ArrayList<>(data.values());

    }

    @Override
    public void updateUser(UUID id, String nickname, String email, String currentPassword, String newPassword, String description) {

        if (email.isBlank() || nickname.isBlank() || newPassword.isBlank()) {
            throw new IllegalArgumentException("Invalid user data.");
        }

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("No such user.");
        }

        if (existUserByEmail(email) && !data.get(id).getEmail().equals(email)) {
            throw new IllegalArgumentException("Email already exists.");
        }

        if (!securityUtil.hashPassword(currentPassword).equals(data.get(id).getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        data.get(id).update(nickname, email, securityUtil.hashPassword(newPassword), description);

    }

    @Override
    public void deleteUserById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("No such user.");
        }

        data.remove(id);

    }
}
