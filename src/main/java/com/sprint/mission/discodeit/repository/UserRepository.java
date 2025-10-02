package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existById(UUID id);

    boolean existByEmail(String email);

    boolean existByNickname(String nickname);

    boolean existByEmailOrUsername(String email, String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.profileId LEFT JOIN FETCH u.userStatus WHERE u.id = :id")
    Optional<User> findById(UUID id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.profileId LEFT JOIN FETCH u.userStatus WHERE u.username = :username")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.profileId LEFT JOIN FETCH u.userStatus WHERE u.username = :username")
    Optional<User> findByUsername(String username);

    void deleteById(UUID id);

}
