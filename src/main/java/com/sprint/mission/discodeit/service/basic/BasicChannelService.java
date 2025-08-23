package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.DiscordDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
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

        channelRepository.save(channel);

    }

    @Override
    public void addUserToChannel(UUID channelId, User user) {

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such user.")).getUserMap().containsKey(user.getId())) {
            throw new IllegalArgumentException("User already exists in channel.");
        }

        Channel updatedChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));
        updatedChannel.getUserMap().put(user.getId(), user);

        channelRepository.save(updatedChannel);

    }

    @Override
    public void addMessageToChannel(UUID channelId, Message message) {

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        Channel updatedChannel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));
        updatedChannel.getMessageMap().put(message.getId(), message);

        channelRepository.save(updatedChannel);

    }

    @Override
    public boolean existChannelById(UUID id) {
        return channelRepository.existById(id);
    }

    @Override
    public Optional<Channel> findChannelById(UUID id) {

        if (!channelRepository.existById(id)) {
            throw new IllegalArgumentException("No such channel.");
        }

        return channelRepository.findById(id);

    }

    @Override
    public List<Channel> findChannelsByUserId(UUID userId) {
        return findAllChannels().stream()
                .filter(channel -> channel.getUserMap().containsKey(userId))
                .toList();
    }

    @Override
    public List<Channel> findAllChannels() {
        return channelRepository.findAll();
    }

    @Override
    public void updateChannel(DiscordDTO.UpdateChannelRequest request) {

        if (!channelRepository.existById(request.id())) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (request.channelName().isBlank() || request.category() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        Channel updatedChannel = channelRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));

        updatedChannel.update(request.channelName(), request.category(), request.isVoiceChannel());
        channelRepository.save(updatedChannel);

    }

    @Override
    public void deleteChannelById(UUID id) {

        if (!channelRepository.existById(id)) {
            throw new IllegalArgumentException("No such channel.");
        }

        channelRepository.deleteById(id);

    }

    @Override
    public void deleteUserFromChannel(UUID channelId, UUID userId) {

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));

        channel.getUserMap().remove(userId);
        channelRepository.save(channel);

    }

    @Override
    public void deleteMessageFromChannel(UUID channelId, UUID messageId) {

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such channel."));

        channel.getMessageMap().remove(messageId);
        channelRepository.save(channel);

    }
}
