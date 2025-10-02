package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReadStatusEntityMapper {

  @Mappings({
      @Mapping(target = "user", source = "user"),
      @Mapping(target = "channel", source = "channel")
  })
  ReadStatusDTO.ReadStatus entityToReadStatus(ReadStatusEntity readStatusEntity);

  @Mappings({
      @Mapping(target = "user", source = "user"),
      @Mapping(target = "channel", source = "channel")
  })
  ReadStatusEntity readStatusToEntity(ReadStatusDTO.ReadStatus readStatusDTO);

}
