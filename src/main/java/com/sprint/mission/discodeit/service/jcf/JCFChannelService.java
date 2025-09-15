package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {

    private final Map<UUID, Channel> data;

    public JCFChannelService() {
        this.data = new TreeMap<>();
    }

    @Override
    public void createChannel(ChannelDTO.CreatePublicChannelCommand request) {

        if (request.channelName().isBlank() || request.category() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        Channel channel = new Channel.Builder()
                .channelName(request.channelName())
                .category(request.category())
                .isVoiceChannel(request.isVoiceChannel())
                .build();

        data.put(channel.getId(), channel);

    }

    @Override
    public void createPrivateChannel(ChannelDTO.CreatePrivateChannelCommand request) {

        Channel channel = new Channel.Builder()
                .category(request.category())
                .isPrivate(true)
                .build();

        if (data.containsKey(channel.getId())) {
            throw new IllegalArgumentException("Channel already exists.");
        }

        if (channel.getCategory() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        data.put(channel.getId(), channel);

    }

    @Override
    public boolean existChannelById(UUID id) {
        return data.containsKey(id);
    }

    @Override
    public Optional<ChannelDTO.FindChannelResult> findChannelById(UUID id) {

        if (!data.containsKey(id)) {
            return Optional.empty();
        }

        Channel channel = data.get(id);

        ChannelDTO.FindChannelResult findChannelResult = ChannelDTO.FindChannelResult.builder()
                .id(channel.getId())
                .channelName(channel.getChannelName())
                .category(channel.getCategory())
                .isVoiceChannel(channel.isVoiceChannel())
                .isPrivate(channel.isPrivate())
                .createdAt(channel.getCreatedAt())
                .updatedAt(channel.getUpdatedAt())
                .build();

        return Optional.ofNullable(findChannelResult);

    }

    @Override
    public List<ChannelDTO.FindChannelResult> findChannelsByUserId(UUID userId) {
        return List.of();
    }

    @Override
    public List<ChannelDTO.FindChannelResult> findAllChannels() {
        return new ArrayList<>(data.values().stream().map(channel -> ChannelDTO.FindChannelResult.builder()
                .id(channel.getId())
                .channelName(channel.getChannelName())
                .isVoiceChannel(channel.isVoiceChannel())
                .isPrivate(channel.isPrivate())
                .createdAt(channel.getCreatedAt())
                .updatedAt(channel.getUpdatedAt())
                .category(channel.getCategory()).build()).toList());
    }

    @Override
    public void updateChannel(ChannelDTO.UpdateChannelCommand request) {

        if (!data.containsKey(request.id())) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (request.channelName().isBlank() || request.category() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        if (data.get(request.id()).isPrivate()) {
            throw new IllegalArgumentException("Private channel cannot be updated.");
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

}
