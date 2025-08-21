package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicMessageService implements MessageService {



    @Override
    public void createMessage(Message message) {

    }

    @Override
    public boolean existMessageById(UUID id) {
        return false;
    }

    @Override
    public Optional<Message> findMessageById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Message> findChildMessagesById(UUID id) {
        return List.of();
    }

    @Override
    public List<Message> findAllMessages() {
        return List.of();
    }

    @Override
    public void updateMessage(UUID id, String content, boolean isReply, UUID parentMessageId) {

    }

    @Override
    public void deleteMessageById(UUID id) {

    }
}
