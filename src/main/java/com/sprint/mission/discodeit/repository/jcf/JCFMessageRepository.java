package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.Map;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {

    private final Map<UUID, Message> data;

    public JCFMessageRepository(Map<UUID, Message> data) {
        this.data = data;
    }

    @Override
    public void save(Message message) {
        data.put(message.getId(), message);
    }

    @Override
    public void saveAll(Iterable<Message> messages) {
        messages.forEach(message -> data.put(message.getId(), message));
    }
}
