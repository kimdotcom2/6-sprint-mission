package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository {

    void save(Channel channel);

    void saveAll(Iterable<Channel> channels);

    boolean existById(String id);

    Optional<Channel> findById(String id);

    List<Channel> findAll();

    void deleteById(String id);

}
