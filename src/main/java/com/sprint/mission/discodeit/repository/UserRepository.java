package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

  boolean existsById(UUID id);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsByEmailOrUsername(String email, String username);

  @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.profileId LEFT JOIN FETCH u.userStatus WHERE u.id = :id")
  Optional<UserEntity> findById(UUID id);

  @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.profileId LEFT JOIN FETCH u.userStatus WHERE u.email = :email")
  Optional<UserEntity> findByEmail(String email);

  @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.profileId LEFT JOIN FETCH u.userStatus WHERE u.username = :username")
  Optional<UserEntity> findByUsername(String username);

  void deleteById(UUID id);

}
