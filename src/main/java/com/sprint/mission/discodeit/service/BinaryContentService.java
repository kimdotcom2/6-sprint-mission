package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.BinaryContentDTO.BinaryContentCreateCommand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BinaryContentService {

  BinaryContentDTO.BinaryContent createBinaryContent(BinaryContentCreateCommand request);

  boolean existBinaryContentById(UUID id);

  Optional<BinaryContentDTO.BinaryContent> findBinaryContentById(UUID id);

  List<BinaryContentDTO.BinaryContent> findAllBinaryContentByIdIn(List<UUID> uuidList);

  List<BinaryContentDTO.BinaryContent> findAllBinaryContent();

  void deleteBinaryContentById(UUID id);

}
