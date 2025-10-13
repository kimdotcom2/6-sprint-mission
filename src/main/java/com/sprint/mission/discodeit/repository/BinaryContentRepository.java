package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContentEntity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinaryContentRepository extends JpaRepository<BinaryContentEntity, UUID> {

  boolean existsById(UUID id);

  Optional<BinaryContentEntity> findById(UUID id);

  List<BinaryContentEntity> findAllByIdIn(List<UUID> uuidList);

  void deleteById(UUID id);

  void deleteAllByIdIn(Collection<UUID> idList);

}
