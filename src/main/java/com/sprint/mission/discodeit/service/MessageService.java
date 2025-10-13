package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.MessageDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

  MessageDTO.Message createMessage(MessageDTO.CreateMessageCommand request);

  boolean existMessageById(UUID id);

  Optional<MessageDTO.Message> findMessageById(UUID id);

  List<MessageDTO.Message> findMessagesByAuthorId(UUID authorId);

  List<MessageDTO.Message> findMessagesByChannelId(UUID channelId);

  List<MessageDTO.Message> findAllMessages();

  void updateMessage(MessageDTO.UpdateMessageCommand request);

  void deleteMessageById(UUID id);

}
