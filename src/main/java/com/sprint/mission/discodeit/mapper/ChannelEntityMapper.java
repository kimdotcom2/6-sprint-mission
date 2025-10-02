package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.entity.ChannelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelEntityMapper {

  ChannelDTO.Channel entityToChannel(ChannelEntity channelEntity);

  ChannelEntity channelToEntity(ChannelDTO.Channel channelDTO);

}
