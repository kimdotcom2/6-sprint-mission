package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.ChannelEntity;
import com.sprint.mission.discodeit.enums.ChannelType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<ChannelEntity, UUID> {

  boolean existsById(UUID id);

  Optional<ChannelEntity> findById(UUID id);

  List<ChannelEntity> findByType(ChannelType type);

  void deleteById(UUID id);

}
