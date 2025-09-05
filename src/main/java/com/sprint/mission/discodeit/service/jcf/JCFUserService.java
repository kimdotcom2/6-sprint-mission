package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.dto.UserDTO;
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
    public void createUser(UserDTO.CreateUserRequest request) {

        if (existUserByEmail(request.email()) || existUserByNickname(request.nickname())) {
            throw new IllegalArgumentException("User already exists.");
        }

        User user = new User.Builder()
                .email(request.email())
                .password(request.password())
                .nickname(request.nickname())
                .description(request.description())
                .build();

        user.updatePassword(securityUtil.hashPassword(user.getPassword()));
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
    public boolean existUserByNickname(String nickname) {

        for (User existingUser : data.values()) {
            if (existingUser.getNickname().equals(nickname)) {
                return true;
            }
        }

        return false;

    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserById(UUID id) {

        if (!data.containsKey(id)) {
            return Optional.empty();
        }

        User user = data.get(id);

        return Optional.ofNullable(UserDTO.FindUserResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build());

    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserByEmail(String email) {

        if (!existUserByEmail(email)) {
            return Optional.empty();
        }

        User user = data.entrySet().stream()
                .filter(entry -> entry.getValue().getEmail().equals(email))
                .findFirst().map(Map.Entry::getValue).orElseThrow(() -> new IllegalArgumentException("No such user."));

        return Optional.ofNullable(UserDTO.FindUserResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build());

    }

    @Override
    public Optional<UserDTO.FindUserResult> findUserByNickname(String nickname) {

        if (!existUserByNickname(nickname)) {
            return Optional.empty();
        }

        User user = data.entrySet().stream()
                .filter(entry -> entry.getValue().getNickname().equals(nickname))
                .findFirst().map(Map.Entry::getValue).orElseThrow(() -> new IllegalArgumentException("No such user."));

        return Optional.ofNullable(UserDTO.FindUserResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build());

    }

    @Override
    public List<UserDTO.FindUserResult> findAllUsers() {

        List<User> userList = new ArrayList<>(data.values());

        return userList.stream().map(user -> UserDTO.FindUserResult.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build()).toList();
    }

    @Override
    public void updateUser(UserDTO.UpdateUserRequest request) {

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
