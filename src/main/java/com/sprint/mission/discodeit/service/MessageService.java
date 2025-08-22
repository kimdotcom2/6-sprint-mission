package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.DiscordDTO;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {

    void createMessage(Message message);

    boolean existMessageById(UUID id);

    Optional<Message> findMessageById(UUID id);

    List<Message> findChildMessagesById(UUID id);

    List<Message> findMessagesByUserId(UUID userId);

    List<Message> findMessagesByChannelId(UUID channelId);

    List<Message> findAllMessages();

    void updateMessage(DiscordDTO.UpdateMessageRequest request);

    void deleteMessageById(UUID id);

}
