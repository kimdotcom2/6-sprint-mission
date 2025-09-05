package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ChannelDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {

    void createChannel(ChannelDTO.CreatePublicChannelRequest request);

    void createPrivateChannel(ChannelDTO.CreatePrivateChannelRequest request);

    //void addUserToChannel(UUID channelId, User user);

    //void addMessageToChannel(UUID channelId, Message message);

    boolean existChannelById(UUID id);

    Optional<ChannelDTO.FindChannelResult> findChannelById(UUID id);

    List<ChannelDTO.FindChannelResult> findChannelsByUserId(UUID userId);

    List<ChannelDTO.FindChannelResult> findAllChannels();

    void updateChannel(ChannelDTO.UpdateChannelRequest request);

    void deleteChannelById(UUID id);

    //void deleteUserFromChannel(UUID channelId, UUID userId);

    //void deleteMessageFromChannel(UUID channelId, UUID messageId);

}
