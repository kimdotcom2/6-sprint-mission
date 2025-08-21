package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Override
    public boolean existById(String id) {
        return false;
    }

    @Override
    public Optional<Channel> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Channel> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(String id) {

    }
}
