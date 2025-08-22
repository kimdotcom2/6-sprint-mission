package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository {

    void save(Channel channel);

    void saveAll(Iterable<Channel> channels);

    boolean existById(UUID id);

    Optional<Channel> findById(UUID id);

    List<Channel> findByUserId(UUID userId);

    List<Channel> findAll();

    void deleteById(UUID id);

}
