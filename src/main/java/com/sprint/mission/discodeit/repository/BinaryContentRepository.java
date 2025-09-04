package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BinaryContentRepository {

    void save(BinaryContent binaryContent);

    void saveAll(Iterable<BinaryContent> binaryContents);

    boolean existById(UUID id);

    Optional<BinaryContent> findById(UUID id);

    List<BinaryContent> findAll();

    void deleteById(UUID id);

    void deleteAll(Iterable<UUID> binaryContents);

}
