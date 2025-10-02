package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MessageEntityMapper {

  @Mappings({
      @Mapping(target = "channel", source = "channel"),
      @Mapping(target = "author", source = "author"),
      @Mapping(target = "attachments", source = "binaryContentList")
  })
  MessageDTO.Message entityToMessage(MessageEntity messageEntity);

  @Mappings({
      @Mapping(target = "channel", source = "channel"),
      @Mapping(target = "author", source = "author"),
      @Mapping(target = "binaryContentList", source = "attachments")
  })
  MessageEntity messageToEntity(MessageDTO.Message messageDTO);

}
