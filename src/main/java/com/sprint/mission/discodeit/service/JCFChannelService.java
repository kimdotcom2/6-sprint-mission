package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface JCFChannelService {

    void create(Channel channel);

    boolean existById(UUID id);

    Channel readById(UUID id);

    List<Channel> readAll();

    void update(UUID id, String channelName, String category, boolean isVoiceChannel);

    void deleteById(UUID id);

}
