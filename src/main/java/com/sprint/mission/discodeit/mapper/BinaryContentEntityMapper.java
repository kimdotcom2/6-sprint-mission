package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.entity.BinaryContentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BinaryContentEntityMapper {

  BinaryContentDTO.BinaryContent entityToBinaryContent(BinaryContentEntity binaryContentEntity);

  BinaryContentEntity binaryContentToEntity(BinaryContentDTO.BinaryContent binaryContentDTO);

}
