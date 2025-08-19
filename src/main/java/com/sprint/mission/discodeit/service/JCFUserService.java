package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

public interface JCFUserService {

    void create(User user);

    boolean existById(String id);

    void readById(String id);

    void readAll();

    void update(String id, String nickname, String email, String password, String description);

    void deleteById(String id);

}
