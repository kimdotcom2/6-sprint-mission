package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.DiscordDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.enums.ChannelType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {

    void createChannel(Channel channel);

    void addUserToChannel(UUID channelId, User user);

    void addMessageToChannel(UUID channelId, Message message);

    boolean existChannelById(UUID id);

    Optional<Channel> findChannelById(UUID id);

    List<Channel> findChannelsByUserId(UUID userId);

    List<Channel> findAllChannels();

    void updateChannel(DiscordDTO.UpdateChannelRequest request);

    void deleteChannelById(UUID id);

    void deleteUserFromChannel(UUID channelId, UUID userId);

    void deleteMessageFromChannel(UUID channelId, UUID messageId);

}
