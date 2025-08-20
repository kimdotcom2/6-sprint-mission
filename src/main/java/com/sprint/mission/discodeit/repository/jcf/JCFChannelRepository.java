package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.Map;
import java.util.UUID;

public class JCFChannelRepository implements ChannelRepository {

    private final Map<UUID, Channel> data;

    public JCFChannelRepository(Map<UUID, Channel> data) {
        this.data = data;
    }

    @Override
    public void save(Channel channel) {
        data.put(channel.getId(), channel);
    }

    @Override
    public void saveAll(Iterable<Channel> channels) {
        channels.forEach(channel -> data.put(channel.getId(), channel));
    }
}
