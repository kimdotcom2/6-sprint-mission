package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {

    void create(Channel channel);

    boolean existById(UUID id);

    Optional<Channel> findChannelById(UUID id);

    List<Channel> findAllChannels();

    void update(UUID id, String channelName, String category, boolean isVoiceChannel);

    void deleteById(UUID id);

}
