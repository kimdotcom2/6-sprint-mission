package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.MessageDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    void createMessage(MessageDTO.CreateMessageCommand request);

    boolean existMessageById(UUID id);

    Optional<MessageDTO.FindMessageResult> findMessageById(UUID id);

    List<MessageDTO.FindMessageResult> findChildMessagesById(UUID id);

    List<MessageDTO.FindMessageResult> findMessagesByUserId(UUID userId);

    List<MessageDTO.FindMessageResult> findMessagesByChannelId(UUID channelId);

    List<MessageDTO.FindMessageResult> findAllMessages();

    void updateMessage(MessageDTO.UpdateMessageCommand request);

    void deleteMessageById(UUID id);

}
