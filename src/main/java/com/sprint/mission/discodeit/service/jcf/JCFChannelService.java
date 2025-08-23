package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.dto.DiscordDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {

    private final Map<UUID, Channel> data;

    public JCFChannelService() {
        this.data = new TreeMap<>();
    }

    @Override
    public void createChannel(Channel channel) {

        if (data.containsKey(channel.getId())) {
            throw new IllegalArgumentException("Channel already exists.");
        }

        if (channel.getChannelName().isBlank() || channel.getCategory() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        data.put(channel.getId(), channel);

    }

    @Override
    public void addUserToChannel(UUID channelId, User user) {

        if (!data.containsKey(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (data.get(channelId).getUserMap().containsKey(user.getId())) {
            throw new IllegalArgumentException("User already exists in channel.");
        }

        data.get(channelId).getUserMap().put(user.getId(), user);

    }

    @Override
    public void addMessageToChannel(UUID channelId, Message message) {

        if (!data.containsKey(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        data.get(channelId).getMessageMap().put(message.getId(), message);

    }

    @Override
    public boolean existChannelById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public Optional<Channel> findChannelById(UUID id) {

        if (!data.containsKey(id)) {
            return Optional.empty();
        }

        Channel channel = data.get(id);

        return Optional.ofNullable(channel);

    }

    @Override
    public List<Channel> findChannelsByUserId(UUID userId) {
        return findAllChannels().stream()
                .filter(channel -> channel.getUserMap().containsKey(userId))
                .toList();
    }

    @Override
    public List<Channel> findAllChannels() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateChannel(DiscordDTO.UpdateChannelRequest request) {

        if (!data.containsKey(request.id())) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (request.channelName().isBlank() || request.category() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        data.get(request.id()).update(request.channelName(), request.category(), request.isVoiceChannel());

    }

    @Override
    public void deleteChannelById(UUID id) {

        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("No such channel.");
        }

        data.remove(id);

    }

    @Override
    public void deleteUserFromChannel(UUID channelId, UUID userId) {

        if (!data.containsKey(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        data.get(channelId).getUserMap().remove(userId);

    }

    @Override
    public void deleteMessageFromChannel(UUID channelId, UUID messageId) {

        if (!data.containsKey(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        data.get(channelId).getMessageMap().remove(messageId);

    }
}
