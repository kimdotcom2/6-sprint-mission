package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.UUID;

public interface JCFUserService {

    void create(User user);

    boolean existById(UUID id);

    void readById(UUID id);

    void readAll();

    void update(UUID id, String nickname, String email, String password, String description);

    void deleteById(UUID id);

}
