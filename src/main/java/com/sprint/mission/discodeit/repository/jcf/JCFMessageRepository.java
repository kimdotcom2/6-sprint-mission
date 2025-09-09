package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "code.repository.type", havingValue = "jcf")
public class JCFMessageRepository implements MessageRepository {

    private final Map<UUID, Message> data;

    public JCFMessageRepository() {
        data = new HashMap<>();
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
                .filter(Message::isReply)
                .filter(message -> message.getParentMessageId().equals(id))
                .toList();
    }

    @Override
    public List<Message> findByUserId(UUID userId) {
        return findAll().stream()
                .filter(message -> message.getUserId().equals(userId))
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .toList();
    }

    @Override
    public List<Message> findByChannelId(UUID channelId) {
        return findAll().stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .sorted(Comparator.comparing(Message::getCreatedAt))
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

    @Override
    public void deleteByChannelId(UUID channelId) {
        findByChannelId(channelId).forEach(message -> deleteById(message.getId()));
    }
}
