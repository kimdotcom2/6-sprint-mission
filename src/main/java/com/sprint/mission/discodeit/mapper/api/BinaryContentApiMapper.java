package com.sprint.mission.discodeit.mapper.api;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.BinaryContentDTO.BinaryContent;
import com.sprint.mission.discodeit.dto.api.BinaryContentApiDTO;
import org.springframework.stereotype.Component;

@Component
public class BinaryContentApiMapper {

  public BinaryContentApiDTO.ReadBinaryContentResponse binaryContentToReadBinaryContentResponse(BinaryContent binaryContent){

    return BinaryContentApiDTO.ReadBinaryContentResponse.builder()
        .id(binaryContent.getId())
        .fileName(binaryContent.getFileName())
        .size(binaryContent.getSize())
        .contentType(binaryContent.getContentType())
        .build();

  }

}
