package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.MessageEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

  boolean existById(UUID id);

  @Query("SELECT m FROM MessageEntity m LEFT JOIN FETCH m.author LEFT JOIN FETCH m.channel WHERE m.id = :id")
  Optional<MessageEntity> findById(UUID id);

  @Query("SELECT m FROM MessageEntity m LEFT JOIN FETCH m.author LEFT JOIN FETCH m.channel WHERE m.author.id = :authorId")
  List<MessageEntity> findByAuthorId(UUID authorId);

  @Query("SELECT m FROM MessageEntity m LEFT JOIN FETCH m.author LEFT JOIN FETCH m.channel WHERE m.channel.id = :channelId")
  List<MessageEntity> findByChannelId(UUID channelId);

  void deleteById(UUID id);

  void deleteByChannelId(UUID channelId);

}
