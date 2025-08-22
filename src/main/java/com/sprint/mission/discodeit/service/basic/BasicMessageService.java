package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.DiscordDTO;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public BasicMessageService(UserRepository userRepository, ChannelRepository channelRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void createMessage(Message message) {

        if (!userRepository.existById(message.getUserId())) {
            throw new IllegalArgumentException("No such user.");
        }

        if (!channelRepository.existById(message.getChannelId())) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (messageRepository.existById(message.getId())) {
            throw new IllegalArgumentException("Message already exists.");
        }

        messageRepository.save(message);

    }

    @Override
    public boolean existMessageById(UUID id) {
        return messageRepository.existById(id);
    }

    @Override
    public Optional<Message> findMessageById(UUID id) {

        if (!messageRepository.existById(id)) {
            throw new IllegalArgumentException("No such message.");
        }

        return messageRepository.findById(id);
    }

    @Override
    public List<Message> findChildMessagesById(UUID id) {

        if (!messageRepository.existById(id)) {
            throw new IllegalArgumentException("No such message.");
        }

        return messageRepository.findChildById(id);
    }

    @Override
    public List<Message> findMessagesByUserId(UUID userId) {

        if (!userRepository.existById(userId)) {
            throw new IllegalArgumentException("No such user.");
        }

        return messageRepository.findByUserId(userId);

    }

    @Override
    public List<Message> findMessagesByChannelId(UUID channelId) {

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        return messageRepository.findByChannelId(channelId);

    }

    @Override
    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public void updateMessage(DiscordDTO.UpdateMessageRequest request) {

        if (!messageRepository.existById(request.id())) {
            throw new IllegalArgumentException("No such message.");
        }

        if (request.isReply() && (request.parentMessageId() == null || !messageRepository.existById(request.id()))) {
            throw new IllegalArgumentException("No such parent message.");
        }

        if (request.parentMessageId() != null && !request.isReply()) {
            throw new IllegalArgumentException("No reply message.");
        }

        Message updatedMessage = messageRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("No such message."));
        updatedMessage.update(request.content(), request.isReply(), request.parentMessageId());
        messageRepository.save(updatedMessage);

    }

    @Override
    public void deleteMessageById(UUID id) {

        if (!messageRepository.existById(id)) {
            throw new IllegalArgumentException("No such message.");
        }

        messageRepository.deleteById(id);

    }
}
