package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.dto.MessageDTO;
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
    public void createMessage(MessageDTO.CreateMessageCommand request) {

        if (!userService.existUserById(request.userId())) {
            throw new IllegalArgumentException("No such user.");
        }

        if (!channelService.existChannelById(request.channelId())) {
            throw new IllegalArgumentException("No such channel.");
        }

        Message message = new Message.Builder()
                .userId(request.userId())
                .channelId(request.channelId())
                .content(request.content())
                .isReply(request.isReply())
                .parentMessageId(request.parentMessageId())
                .build();

        data.put(message.getId(), message);

    }

    @Override
    public boolean existMessageById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public Optional<MessageDTO.FindMessageResult> findMessageById(UUID id) {

        if (!data.containsKey(id)) {
            return Optional.empty();
        }

        Message message = data.get(id);

        return Optional.ofNullable(MessageDTO.FindMessageResult.builder()
                .id(message.getId())
                .userId(message.getUserId())
                .channelId(message.getChannelId())
                .content(message.getContent())
                .isReply(message.isReply())
                .parentMessageId(message.getParentMessageId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .build());

    }

    @Override
    public List<MessageDTO.FindMessageResult> findChildMessagesById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("No such message.");
        }

        return data.values().stream()
                .filter(message -> message.isReply() && message.getParentMessageId() != null && message.getParentMessageId().equals(id))
                .map(message -> MessageDTO.FindMessageResult.builder()
                        .id(message.getId())
                        .userId(message.getUserId())
                        .channelId(message.getChannelId())
                        .content(message.getContent())
                        .isReply(message.isReply())
                        .parentMessageId(message.getParentMessageId())
                        .createdAt(message.getCreatedAt())
                        .updatedAt(message.getUpdatedAt())
                        .build())
                .toList();

    }

    @Override
    public List<MessageDTO.FindMessageResult> findMessagesByUserId(UUID userId) {

        if (!userService.existUserById(userId)) {
            throw new IllegalArgumentException("No such user.");
        }

        return findAllMessages().stream()
                .filter(message -> message.userId().equals(userId))
                .map(message -> MessageDTO.FindMessageResult.builder()
                        .id(message.id())
                        .userId(message.userId())
                        .channelId(message.channelId())
                        .content(message.content())
                        .isReply(message.isReply())
                        .parentMessageId(message.parentMessageId())
                        .createdAt(message.createdAt())
                        .updatedAt(message.updatedAt())
                        .build())
                .toList();

    }

    @Override
    public List<MessageDTO.FindMessageResult> findMessagesByChannelId(UUID channelId) {

        if (!channelService.existChannelById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        return findAllMessages().stream()
                .filter(message -> message.channelId().equals(channelId))
                .sorted(Comparator.comparing(MessageDTO.FindMessageResult::createdAt))
                .map(message -> MessageDTO.FindMessageResult.builder()
                        .id(message.id())
                        .userId(message.userId())
                        .channelId(message.channelId())
                        .content(message.content())
                        .isReply(message.isReply())
                        .parentMessageId(message.parentMessageId())
                        .createdAt(message.createdAt())
                        .updatedAt(message.updatedAt())
                        .build())
                .toList();

    }

    @Override
    public List<MessageDTO.FindMessageResult> findAllMessages() {
        return new ArrayList<>(data.values().stream()
                .map(message -> MessageDTO.FindMessageResult.builder()
                        .id(message.getId())
                        .userId(message.getUserId())
                        .channelId(message.getChannelId())
                        .content(message.getContent())
                        .isReply(message.isReply())
                        .parentMessageId(message.getParentMessageId())
                        .createdAt(message.getCreatedAt())
                        .updatedAt(message.getUpdatedAt())
                        .build()).toList());
    }

    @Override
    public void updateMessage(MessageDTO.UpdateMessageCommand request) {

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
