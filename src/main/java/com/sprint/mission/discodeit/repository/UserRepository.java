package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    void save(User user);

    void saveAll(Iterable<User> users);

    boolean existById(UUID id);

    boolean existByEmail(String email);

    boolean existByNickname(String nickname);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    List<User> findAll();

    void deleteById(UUID id);

}
