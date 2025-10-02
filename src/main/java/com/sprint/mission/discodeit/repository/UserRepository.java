package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existById(UUID id);

    boolean existByEmail(String email);

    boolean existByNickname(String nickname);

    boolean existByEmailOrUsername(String email, String username);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    void deleteById(UUID id);

}
