package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;

    @Override
    public void createChannel(ChannelDTO.CreatePublicChannelCommand request) {

        if (request.channelName().isBlank() || request.category() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        Channel channel = new Channel.Builder()
                .channelName(request.channelName())
                .category(request.category())
                .isVoiceChannel(request.isVoiceChannel())
                .description(request.description())
                .build();

        channelRepository.save(channel);

    }

    @Override
    public void createPrivateChannel(ChannelDTO.CreatePrivateChannelCommand request) {

        Channel channel = new Channel.Builder()
                .category(request.category())
                .isPrivate(true)
                .description(request.description())
                .build();

        List<ReadStatus> readStatusList = request.userIdList().stream()
                .map(userId -> new ReadStatus(channel.getId(), userId, Instant.now().toEpochMilli()))
                .toList();

        readStatusRepository.saveAll(readStatusList);
        channelRepository.save(channel);

    }

    @Override
    public boolean existChannelById(UUID id) {
        return channelRepository.existById(id);
    }

    @Override
    public Optional<ChannelDTO.FindChannelResult> findChannelById(UUID id) {

        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("No such channel."));

        return Optional.of(ChannelDTO.FindChannelResult.builder()
                .id(channel.getId())
                .channelName(channel.getChannelName())
                .category(channel.getCategory())
                .isVoiceChannel(channel.isVoiceChannel())
                .isPrivate(channel.isPrivate())
                .userIdList(channel.isPrivate() ? readStatusRepository.findByChannelId(channel.getId()).stream()
                        .map(ReadStatus::getUserId).toList() : new ArrayList<>())
                .recentMessageTime(messageRepository.findByChannelId(channel.getId()).stream()
                        .max(Comparator.comparing(Message::getCreatedAt)).map(Message::getCreatedAt).orElse(null))
                .description(channel.getDescription())
                .createdAt(channel.getCreatedAt())
                .updatedAt(channel.getUpdatedAt())
                .build());

    }

    @Override
    public List<ChannelDTO.FindChannelResult> findChannelsByUserId(UUID userId) {

        List<ChannelDTO.FindChannelResult> privateChannelList = readStatusRepository.findByUserId(userId).stream()
                .map(readStatus -> channelRepository.findById(readStatus.getChannelId())
                        .orElseThrow(() -> new NoSuchDataException("No such channel.")))
                .map(channel -> ChannelDTO.FindChannelResult.builder()
                        .id(channel.getId())
                        .channelName(channel.getChannelName())
                        .category(channel.getCategory())
                        .isVoiceChannel(channel.isVoiceChannel())
                        .isPrivate(channel.isPrivate())
                        .userIdList(channel.isPrivate() ? 
                                readStatusRepository.findByChannelId(channel.getId()).stream()
                                        .map(ReadStatus::getUserId)
                                        .toList() : 
                                new ArrayList<>())
                        .recentMessageTime(messageRepository.findByChannelId(channel.getId()).stream()
                                .max(Comparator.comparing(Message::getCreatedAt))
                                .map(Message::getCreatedAt)
                                .orElse(null))
                        .description(channel.getDescription())
                        .createdAt(channel.getCreatedAt())
                        .updatedAt(channel.getUpdatedAt())
                        .build())
                .toList();

        List<ChannelDTO.FindChannelResult> publicChannelList = channelRepository.findAll().stream()
                .filter(channel -> !channel.isPrivate())
                .map(channel -> ChannelDTO.FindChannelResult.builder()
                        .id(channel.getId())
                        .channelName(channel.getChannelName())
                        .category(channel.getCategory())
                        .isVoiceChannel(channel.isVoiceChannel())
                        .isPrivate(channel.isPrivate())
                        .userIdList(new ArrayList<>())
                        .recentMessageTime(messageRepository.findByChannelId(channel.getId()).stream()
                                .max(Comparator.comparing(Message::getCreatedAt))
                                .map(Message::getCreatedAt)
                                .orElse(null))
                        .description(channel.getDescription())
                        .createdAt(channel.getCreatedAt())
                        .updatedAt(channel.getUpdatedAt())
                        .build())
                .toList();

        Set<ChannelDTO.FindChannelResult> combined = new HashSet<>();
        combined.addAll(privateChannelList);
        combined.addAll(publicChannelList);
        
        return new ArrayList<>(combined);
    }

    @Override
    public List<ChannelDTO.FindChannelResult> findAllChannels() {
        return channelRepository.findAll().stream()
                .map(channel -> ChannelDTO.FindChannelResult.builder()
                        .id(channel.getId())
                        .channelName(channel.getChannelName())
                        .category(channel.getCategory())
                        .isVoiceChannel(channel.isVoiceChannel())
                        .isPrivate(channel.isPrivate())
                        .userIdList(channel.isPrivate() ? readStatusRepository.findByChannelId(channel.getId()).stream()
                                .map(ReadStatus::getUserId).toList() : new ArrayList<>())
                        .recentMessageTime(messageRepository.findByChannelId(channel.getId()).stream()
                                .max(Comparator.comparing(Message::getCreatedAt))
                                .map(Message::getCreatedAt).orElse(null))
                        .description(channel.getDescription())
                        .createdAt(channel.getCreatedAt())
                        .updatedAt(channel.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public void updateChannel(ChannelDTO.UpdateChannelCommand request) {

        if (!channelRepository.existById(request.id())) {
            throw new NoSuchDataException("No such channel.");
        }

        if (request.channelName().isBlank() || request.category() == null) {
            throw new IllegalArgumentException("Invalid channel data.");
        }

        Channel updatedChannel = channelRepository.findById(request.id())
                .orElseThrow(() -> new NoSuchDataException("No such channel."));

        if (updatedChannel.isPrivate()) {
            throw new IllegalArgumentException("Private channel cannot be updated.");
        }

        updatedChannel.update(request.channelName(), request.category(), request.isVoiceChannel(), request.description());
        channelRepository.save(updatedChannel);

    }

    @Override
    public void deleteChannelById(UUID id) {

        if (!channelRepository.existById(id)) {
            throw new NoSuchDataException("No such channel.");
        }

        messageRepository.deleteByChannelId(id);
        readStatusRepository.deleteByChannelId(id);
        channelRepository.deleteById(id);

    }

}
