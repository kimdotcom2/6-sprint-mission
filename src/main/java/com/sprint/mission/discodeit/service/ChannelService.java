package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {

    void create(Channel channel);

    void addUserToChannel(UUID channelId, User user);

    void addMessageToChannel(UUID channelId, Message message);

    boolean existById(UUID id);

    Optional<Channel> findChannelById(UUID id);

    List<Channel> findAllChannels();

    void update(UUID id, String channelName, String category, boolean isVoiceChannel);

    void deleteById(UUID id);

    void deleteUserFromChannel(UUID channelId, UUID userId);

    void deleteMessageFromChannel(UUID channelId, UUID messageId);

}
