package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.MessageEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

  boolean existsById(UUID id);

  @Query("SELECT m FROM MessageEntity m LEFT JOIN FETCH m.author LEFT JOIN FETCH m.channel WHERE m.id = :id")
  Optional<MessageEntity> findById(UUID id);

  @Query("SELECT m FROM MessageEntity m LEFT JOIN FETCH m.author LEFT JOIN FETCH m.channel WHERE m.author.id = :authorId ORDER BY m.createdAt ASC")
  Page<MessageEntity> findByAuthorId(UUID authorId, Pageable pageable);

  @Query("SELECT m FROM MessageEntity m LEFT JOIN FETCH m.author LEFT JOIN FETCH m.channel WHERE m.channel.id = :channelId ORDER BY m.createdAt ASC")
  Page<MessageEntity> findByChannelId(UUID channelId, Pageable pageable);

  @Query(value = "SELECT m FROM MessageEntity m LEFT JOIN FETCH m.author LEFT JOIN FETCH m.channel",
      countQuery = "SELECT count(m) FROM MessageEntity m")
  Page<MessageEntity> findAll(Pageable pageable);

  void deleteById(UUID id);

  void deleteByChannelId(UUID channelId);

}
