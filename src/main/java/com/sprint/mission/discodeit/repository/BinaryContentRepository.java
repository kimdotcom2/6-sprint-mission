package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BinaryContentRepository {

    void save(BinaryContent binaryContent);

    void saveAll(Iterable<BinaryContent> binaryContents);

    boolean existById(UUID id);

    Optional<BinaryContent> findById(UUID id);

    List<BinaryContent> findAllByIdIn(List<UUID> uuidList);

    List<BinaryContent> findAll();

    void deleteById(UUID id);

    void deleteAll(Iterable<UUID> binaryContents);

}
