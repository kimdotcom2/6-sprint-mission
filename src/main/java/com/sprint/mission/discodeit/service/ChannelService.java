package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ChannelDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {

    void createChannel(ChannelDTO.CreatePublicChannelCommand request);

    void createPrivateChannel(ChannelDTO.CreatePrivateChannelCommand request);

    boolean existChannelById(UUID id);

    Optional<ChannelDTO.FindChannelResult> findChannelById(UUID id);

    List<ChannelDTO.FindChannelResult> findChannelsByUserId(UUID userId);

    List<ChannelDTO.FindChannelResult> findAllChannels();

    void updateChannel(ChannelDTO.UpdateChannelCommand request);

    void deleteChannelById(UUID id);

}
