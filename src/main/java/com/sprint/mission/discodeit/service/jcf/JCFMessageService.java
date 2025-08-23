package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.dto.DiscordDTO;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFMessageService implements MessageService {

    private final Map<UUID, Message> data;
    private final UserService userService;
    private final ChannelService channelService;

    public JCFMessageService(UserService userService, ChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
        data = new TreeMap<>();
    }

    @Override
    public void createMessage(Message message) {

        if (!userService.existUserById(message.getUserId())) {
            throw new IllegalArgumentException("No such user.");
        }

        if (!channelService.existChannelById(message.getChannelId())) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (data.containsKey(message.getId())) {
            throw new IllegalArgumentException("Message already exists.");
        }

        data.put(message.getId(), message);

    }

    @Override
    public boolean existMessageById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public Optional<Message> findMessageById(UUID id) {

        if (!data.containsKey(id)) {
            return Optional.empty();
        }

        Message message = data.get(id);

        return Optional.ofNullable(message);

    }

    @Override
    public List<Message> findChildMessagesById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("No such message.");
        }

        return data.values().stream()
                .filter(message -> message.isReply() && message.getParentMessageId() != null && message.getParentMessageId().equals(id))
                .toList();

    }

    @Override
    public List<Message> findMessagesByUserId(UUID userId) {

        if (!userService.existUserById(userId)) {
            throw new IllegalArgumentException("No such user.");
        }

        return findAllMessages().stream()
                .filter(message -> message.getUserId().equals(userId))
                .toList();

    }

    @Override
    public List<Message> findMessagesByChannelId(UUID channelId) {

        if (!channelService.existChannelById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        return findAllMessages().stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .toList();

    }

    @Override
    public List<Message> findAllMessages() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateMessage(DiscordDTO.UpdateMessageRequest request) {

        if (!data.containsKey(request.id())) {
            throw new IllegalArgumentException("No such message.");
        }

        if (request.isReply() && (request.parentMessageId() == null || !data.containsKey(request.parentMessageId()))) {
            throw new IllegalArgumentException("No such parent message.");
        }

        if (request.parentMessageId() != null && !request.isReply()) {
            throw new IllegalArgumentException("No reply message.");
        }

        data.get(request.id()).update(request.content(), request.isReply(), request.parentMessageId());

    }

    @Override
    public void deleteMessageById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("No such message.");
        }

        data.remove(id);

    }
}
