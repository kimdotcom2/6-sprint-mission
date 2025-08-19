package com.sprint.mission.discodit.service;

import com.sprint.mission.discodit.entity.Channel;

public interface JCFChannelService {

    void create(Channel channel);

    boolean existById(String id);

    void readById(String id);

    void readAll();

    void update(String id, String channelName, String category, boolean isVoiceChannel);

    void deleteById(String id);

}
