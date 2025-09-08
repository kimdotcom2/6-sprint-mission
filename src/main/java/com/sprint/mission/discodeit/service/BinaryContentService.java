package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BinaryContentService {

    void createBinaryContent(BinaryContentDTO.CreateBinaryContentRequest request);

    boolean existBinaryContentById(UUID id);

    Optional<BinaryContentDTO.ReadBinaryContentResult> findBinaryContentById(UUID id);

    List<BinaryContentDTO.ReadBinaryContentResult> findAllBinaryContentByIdIn(List<UUID> uuidList);

    List<BinaryContentDTO.ReadBinaryContentResult> findAllBinaryContent();

    void deleteBinaryContentById(UUID id);

}
