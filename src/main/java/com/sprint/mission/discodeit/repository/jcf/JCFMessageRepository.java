package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

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

    @Override
    public boolean existById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Message> findChildById(UUID id) {
        return data.values().stream()
                .filter(message -> message.getParentMessageId().equals(id))
                .toList();
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void deleteById(UUID id) {
        data.remove(id);
    }
}
