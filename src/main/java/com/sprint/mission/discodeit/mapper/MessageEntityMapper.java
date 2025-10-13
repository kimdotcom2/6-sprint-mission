package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MessageEntityMapper {

  @Mappings({
      @Mapping(target = "channelId", source = "channel.id"),
      @Mapping(target = "author", source = "author"),
      @Mapping(target = "attachments", source = "attachments")
  })
  MessageDTO.Message entityToMessage(MessageEntity messageEntity);

  @Mappings({
      //@Mapping(target = "channel", source = "channelId"),
      @Mapping(target = "author", source = "author"),
      //@Mapping(target = "attachments", source = "attachments")
  })
  MessageEntity messageToEntity(MessageDTO.Message messageDTO);

}
