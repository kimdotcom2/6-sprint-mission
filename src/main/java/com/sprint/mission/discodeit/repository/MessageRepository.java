package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    void save(Message message);

    void saveAll(Iterable<Message> messages);

    boolean existById(UUID id);

    Optional<Message> findById(UUID id);

    List<Message> findChildById(UUID id);

    List<Message> findByUserId(UUID userId);

    List<Message> findByChannelId(UUID channelId);

    List<Message> findAll();

    void deleteById(UUID id);

    void deleteByChannelId(UUID channelId);

}
