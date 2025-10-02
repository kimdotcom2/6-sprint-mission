package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ReadStatus;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, UUID> {

    boolean existById(UUID id);

    boolean existByUserIdAndChannelId(UUID userId, UUID channelId);

    @Query("SELECT rs FROM ReadStatus rs LEFT JOIN FETCH rs.user u LEFT JOIN FETCH rs.channel c WHERE rs.id = :id")
    Optional<ReadStatus> findById(UUID id);

    @Query("SELECT rs FROM ReadStatus rs LEFT JOIN FETCH rs.user u LEFT JOIN FETCH rs.channel c WHERE u.id = :userId AND c.id = :channelId")
    Optional<ReadStatus> findByUserIdAndChannelId(UUID userId, UUID channelId);

    @Query("SELECT rs FROM ReadStatus rs LEFT JOIN FETCH rs.user u LEFT JOIN FETCH rs.channel c WHERE u.id = :userId")
    List<ReadStatus> findByUserId(UUID userId);

    @Query("SELECT rs FROM ReadStatus rs LEFT JOIN FETCH rs.user u LEFT JOIN FETCH rs.channel c WHERE c.id = :channelId")
    List<ReadStatus> findByChannelId(UUID channelId);

    void deleteById(UUID id);

    void deleteByUserIdAndChannelId(UUID userId, UUID channelId);

    void deleteByUserId(UUID userId);

    void deleteByChannelId(UUID channelId);

    void deleteAllByIdIn(Collection<UUID> idList);

}
