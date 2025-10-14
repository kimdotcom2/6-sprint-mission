package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {

  ChannelDTO.Channel createChannel(ChannelDTO.CreatePublicChannelCommand request);

  ChannelDTO.Channel createPrivateChannel(ChannelDTO.CreatePrivateChannelCommand request);

  boolean existChannelById(UUID id);

  Optional<ChannelDTO.Channel> findChannelById(UUID id);

  List<ChannelDTO.Channel> findChannelsByUserId(UUID userId);

  List<ChannelDTO.Channel> findAllChannels();

  ChannelDTO.Channel updateChannel(ChannelDTO.UpdateChannelCommand request);

  void deleteChannelById(UUID id);

}
