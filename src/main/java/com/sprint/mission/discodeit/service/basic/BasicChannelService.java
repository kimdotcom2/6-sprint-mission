package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;

    public BasicChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public void createChannel(Channel channel) {

        if (channelRepository.existById(channel.getId())) {
            throw new IllegalArgumentException("Channel already exists.");
        }

        if (channel.getChannelName().isBlank() || channel.getCategory() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

    }

    @Override
    public void addUserToChannel(UUID channelId, User user) {

    }

    @Override
    public void addMessageToChannel(UUID channelId, Message message) {

    }

    @Override
    public boolean existChannelById(UUID id) {
        return false;
    }

    @Override
    public Optional<Channel> findChannelById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Channel> findAllChannels() {
        return List.of();
    }

    @Override
    public void updateChannel(UUID id, String channelName, ChannelType category, boolean isVoiceChannel) {

    }

    @Override
    public void deleteChannelById(UUID id) {

    }

    @Override
    public void deleteUserFromChannel(UUID channelId, UUID userId) {

    }

    @Override
    public void deleteMessageFromChannel(UUID channelId, UUID messageId) {

    }
}
