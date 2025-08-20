package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    void create(Message message);

    boolean existById(UUID id);

    Optional<Message> findMessageById(UUID id);

    List<Message> findChildMessagesById(UUID id);

    List<Message> findAllMessages();

    void update(UUID id, String content, boolean isReply, UUID parentMessageId);

    void deleteById(UUID id);

}
