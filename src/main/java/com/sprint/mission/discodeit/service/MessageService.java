package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.MessageDTO;

import com.sprint.mission.discodeit.dto.MessageDTO.Message;
import com.sprint.mission.discodeit.dto.PagingDTO;
import com.sprint.mission.discodeit.dto.PagingDTO.OffsetPage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

  MessageDTO.Message createMessage(MessageDTO.CreateMessageCommand request);

  boolean existMessageById(UUID id);

  Optional<MessageDTO.Message> findMessageById(UUID id);

  OffsetPage<MessageDTO.Message> findMessagesByAuthorId(UUID authorId, PagingDTO.OffsetRequest pageable);

  OffsetPage<MessageDTO.Message> findMessagesByChannelId(UUID channelId, PagingDTO.OffsetRequest pageable);

  OffsetPage<Message> findAllMessages(PagingDTO.OffsetRequest pageable);

  MessageDTO.Message updateMessage(MessageDTO.UpdateMessageCommand request);

  void deleteMessageById(UUID id);

}
