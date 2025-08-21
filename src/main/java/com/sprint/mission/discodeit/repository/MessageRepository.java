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

    List<Message> findAll();

    void deleteById(UUID id);

}
