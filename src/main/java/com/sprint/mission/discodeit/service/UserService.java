package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    void create(User user);

    boolean existById(UUID id);

    Optional<User> findUserById(UUID id);

    List<User> findAllUsers();

    void update(UUID id, String nickname, String email, String password, String description);

    void deleteById(UUID id);

}
