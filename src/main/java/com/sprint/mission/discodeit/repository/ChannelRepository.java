package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ChannelEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<ChannelEntity, UUID> {

    boolean existById(UUID id);

    Optional<ChannelEntity> findById(UUID id);

    void deleteById(UUID id);

}
