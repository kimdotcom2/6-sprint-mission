package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    void createUser(User user);

    boolean existUserById(UUID id);

    boolean existUserByEmail(String email);

    Optional<User> findUserById(UUID id);

    Optional<User> findUserByEmail(String email);

    List<User> findAllUsers();

    void updateUser(UUID id, String nickname, String email, String currentPassword, String newPassword, String description);

    void deleteUserById(UUID id);

}
