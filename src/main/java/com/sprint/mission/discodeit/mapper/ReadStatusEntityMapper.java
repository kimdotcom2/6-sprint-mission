package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReadStatusEntityMapper {

  @Mappings({
      @Mapping(target = "userId", source = "user.id"),
      @Mapping(target = "channelId", source = "channel.id")
  })
  ReadStatusDTO.ReadStatus entityToReadStatus(ReadStatusEntity readStatusEntity);

  @Mappings({
      //@Mapping(target = "user.id", source = "userId"),
      //@Mapping(target = "channel.id", source = "channelId")
  })
  ReadStatusEntity readStatusToEntity(ReadStatusDTO.ReadStatus readStatusDTO);

}
