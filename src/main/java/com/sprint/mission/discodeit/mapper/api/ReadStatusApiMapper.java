package com.sprint.mission.discodeit.mapper.api;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.dto.api.ReadStatusApiDTO;
import org.springframework.stereotype.Component;

@Component
public class ReadStatusApiMapper {

  public ReadStatusApiDTO.FindReadStatusResponse readStatusApiToReadStatusResponse(ReadStatusDTO.ReadStatus readStatus) {
    return ReadStatusApiDTO.FindReadStatusResponse.builder()
        .id(readStatus.getId())
        .channelId(readStatus.getChannelId())
        .userId(readStatus.getUserId())
        .lastReadAt(readStatus.getLastReadAt())
        .build();
  }

}
