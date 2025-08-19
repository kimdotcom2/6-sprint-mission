package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.UUID;

public interface JCFChannelService {

    void create(Channel channel);

    boolean existById(UUID id);

    void readById(UUID id);

    void readAll();

    void update(UUID id, String channelName, String category, boolean isVoiceChannel);

    void deleteById(UUID id);

}
