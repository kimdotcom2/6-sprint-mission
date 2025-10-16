package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatusEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserStatusRepository extends JpaRepository<UserStatusEntity, UUID> {

  boolean existsById(UUID id);

  boolean existsByUserId(UUID userId);

  @Query("SELECT us FROM UserStatusEntity us LEFT JOIN FETCH us.user WHERE us.id = :id")
  Optional<UserStatusEntity> findById(@Param("id") UUID id);

  @Query("SELECT us FROM UserStatusEntity us LEFT JOIN FETCH us.user WHERE us.user.id = :userId")
  Optional<UserStatusEntity> findByUserId(@Param("userId") UUID userId);

  void deleteById(UUID id);

  void deleteByUserId(UUID userId);

  void deleteAllByIdIn(Collection<UUID> idList);

}
