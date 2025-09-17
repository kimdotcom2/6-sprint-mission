package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public void createMessage(MessageDTO.CreateMessageCommand request) {

        if (!userRepository.existById(request.userId())) {
            throw new NoSuchDataException("No such user.");
        }

        if (!channelRepository.existById(request.channelId())) {
            throw new NoSuchDataException("No such channel.");
        }

        Message message = new Message.Builder()
                .userId(request.userId())
                .channelId(request.channelId())
                .content(request.content())
                .isReply(request.isReply())
                .parentMessageId(request.parentMessageId())
                .build();

        messageRepository.save(message);

    }

    @Override
    public boolean existMessageById(UUID id) {
        return messageRepository.existById(id);
    }

    @Override
    public Optional<MessageDTO.FindMessageResult> findMessageById(UUID id) {

        Message message = messageRepository.findById(id).orElseThrow(() -> new NoSuchDataException("No such message."));

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

        if (!messageRepository.existById(id)) {
            throw new NoSuchDataException("No such message.");
        }

        return messageRepository.findChildById(id).stream().map(message -> MessageDTO.FindMessageResult.builder()
                .id(message.getId())
                .userId(message.getUserId())
                .channelId(message.getChannelId())
                .content(message.getContent())
                .isReply(message.isReply())
                .parentMessageId(message.getParentMessageId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .binaryContentList(message.getBinaryContentIdList().stream().toList())
                .build()).toList();
    }

    @Override
    public List<MessageDTO.FindMessageResult> findMessagesByUserId(UUID userId) {

        if (!userRepository.existById(userId)) {
            throw new NoSuchDataException("No such user.");
        }

        return messageRepository.findByUserId(userId).stream().map(message -> MessageDTO.FindMessageResult.builder()
                .id(message.getId())
                .userId(message.getUserId())
                .channelId(message.getChannelId())
                .content(message.getContent())
                .isReply(message.isReply())
                .parentMessageId(message.getParentMessageId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .binaryContentList(message.getBinaryContentIdList().stream().toList())
                .build()).toList();

    }

    @Override
    public List<MessageDTO.FindMessageResult> findMessagesByChannelId(UUID channelId) {

        if (!channelRepository.existById(channelId)) {
            throw new NoSuchDataException("No such channel.");
        }

        return messageRepository.findByChannelId(channelId).stream().map(message -> MessageDTO.FindMessageResult.builder()
                .id(message.getId())
                .userId(message.getUserId())
                .channelId(message.getChannelId())
                .content(message.getContent())
                .isReply(message.isReply())
                .parentMessageId(message.getParentMessageId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .binaryContentList(message.getBinaryContentIdList().stream().toList())
                .build()).toList();

    }

    @Override
    public List<MessageDTO.FindMessageResult> findAllMessages() {
        return messageRepository.findAll().stream().map(message -> MessageDTO.FindMessageResult.builder()
                .id(message.getId())
                .userId(message.getUserId())
                .channelId(message.getChannelId())
                .content(message.getContent())
                .isReply(message.isReply())
                .parentMessageId(message.getParentMessageId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .binaryContentList(message.getBinaryContentIdList().stream().toList())
                .build()).toList();
    }

    @Override
    public void updateMessage(MessageDTO.UpdateMessageCommand request) {

        if (!messageRepository.existById(request.id())) {
            throw new NoSuchDataException("No such message.");
        }

        if (request.isReply() && (request.parentMessageId() == null || !messageRepository.existById(request.parentMessageId()))) {
            throw new NoSuchDataException("No such parent message.");
        }

        if (request.parentMessageId() != null && !request.isReply()) {
            throw new NoSuchDataException("No reply message.");
        }

        Message updatedMessage = messageRepository.findById(request.id())
                .orElseThrow(() -> new NoSuchDataException("No such message."));
        updatedMessage.update(request.content(), request.isReply(), request.parentMessageId());
        messageRepository.save(updatedMessage);

    }

    @Override
    public void deleteMessageById(UUID id) {

        binaryContentRepository.deleteAllByIdIn(messageRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("No such message."))
                .getBinaryContentIdList().stream().toList());
        messageRepository.deleteById(id);

    }
}
