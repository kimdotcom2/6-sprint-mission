package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JCFMessageService {

    void create(Message message);

    boolean existById(UUID id);

    Optional<Message> readById(UUID id);

    List<Message> readChildrenById(UUID id);

    List<Message> readAll();

    void update(UUID id, String content, boolean isReply, UUID parentMessageId);

    void deleteById(UUID id);

}
