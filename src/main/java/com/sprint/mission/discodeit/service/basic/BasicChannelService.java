package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.entity.ChannelEntity;
import com.sprint.mission.discodeit.entity.MessageEntity;
import com.sprint.mission.discodeit.entity.ReadStatusEntity;
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

        ChannelEntity channelEntity = new ChannelEntity.Builder()
                .channelName(request.channelName())
                .category(request.category())
                .isVoiceChannel(request.isVoiceChannel())
                .description(request.description())
                .build();

        channelRepository.save(channelEntity);

    }

    @Override
    public void createPrivateChannel(ChannelDTO.CreatePrivateChannelCommand request) {

        ChannelEntity channelEntity = new ChannelEntity.Builder()
                .category(request.category())
                .isPrivate(true)
                .description(request.description())
                .build();

        List<ReadStatusEntity> readStatusEntityList = request.userIdList().stream()
                .map(userId -> new ReadStatusEntity(channelEntity.getId(), userId, Instant.now().toEpochMilli()))
                .toList();

        readStatusRepository.saveAll(readStatusEntityList);
        channelRepository.save(channelEntity);

    }

    @Override
    public boolean existChannelById(UUID id) {
        return channelRepository.existById(id);
    }

    @Override
    public Optional<ChannelDTO.FindChannelResult> findChannelById(UUID id) {

        ChannelEntity channelEntity = channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("No such channel."));

        return Optional.of(ChannelDTO.FindChannelResult.builder()
                .id(channelEntity.getId())
                .channelName(channelEntity.getChannelName())
                .category(channelEntity.getCategory())
                .isVoiceChannel(channelEntity.isVoiceChannel())
                .isPrivate(channelEntity.isPrivate())
                .userIdList(
                    channelEntity.isPrivate() ? readStatusRepository.findByChannelId(channelEntity.getId()).stream()
                        .map(ReadStatusEntity::getUserId).toList() : new ArrayList<>())
                .recentMessageTime(messageRepository.findByChannelId(channelEntity.getId()).stream()
                        .max(Comparator.comparing(MessageEntity::getCreatedAt)).map(MessageEntity::getCreatedAt).orElse(null))
                .description(channelEntity.getDescription())
                .createdAt(channelEntity.getCreatedAt())
                .updatedAt(channelEntity.getUpdatedAt())
                .build());

    }

    @Override
    public List<ChannelDTO.FindChannelResult> findChannelsByUserId(UUID userId) {

        List<ChannelDTO.FindChannelResult> privateChannelList = readStatusRepository.findByUserId(userId).stream()
                .map(readStatusEntity -> channelRepository.findById(readStatusEntity.getChannelId())
                        .orElseThrow(() -> new NoSuchDataException("No such channel.")))
                .map(channelEntity -> ChannelDTO.FindChannelResult.builder()
                        .id(channelEntity.getId())
                        .channelName(channelEntity.getChannelName())
                        .category(channelEntity.getCategory())
                        .isVoiceChannel(channelEntity.isVoiceChannel())
                        .isPrivate(channelEntity.isPrivate())
                        .userIdList(channelEntity.isPrivate() ?
                                readStatusRepository.findByChannelId(channelEntity.getId()).stream()
                                        .map(ReadStatusEntity::getUserId)
                                        .toList() : 
                                new ArrayList<>())
                        .recentMessageTime(messageRepository.findByChannelId(channelEntity.getId()).stream()
                                .max(Comparator.comparing(MessageEntity::getCreatedAt))
                                .map(MessageEntity::getCreatedAt)
                                .orElse(null))
                        .description(channelEntity.getDescription())
                        .createdAt(channelEntity.getCreatedAt())
                        .updatedAt(channelEntity.getUpdatedAt())
                        .build())
                .toList();

        List<ChannelDTO.FindChannelResult> publicChannelList = channelRepository.findAll().stream()
                .filter(channelEntity -> !channelEntity.isPrivate())
                .map(channelEntity -> ChannelDTO.FindChannelResult.builder()
                        .id(channelEntity.getId())
                        .channelName(channelEntity.getChannelName())
                        .category(channelEntity.getCategory())
                        .isVoiceChannel(channelEntity.isVoiceChannel())
                        .isPrivate(channelEntity.isPrivate())
                        .userIdList(new ArrayList<>())
                        .recentMessageTime(messageRepository.findByChannelId(channelEntity.getId()).stream()
                                .max(Comparator.comparing(MessageEntity::getCreatedAt))
                                .map(MessageEntity::getCreatedAt)
                                .orElse(null))
                        .description(channelEntity.getDescription())
                        .createdAt(channelEntity.getCreatedAt())
                        .updatedAt(channelEntity.getUpdatedAt())
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
                .map(channelEntity -> ChannelDTO.FindChannelResult.builder()
                        .id(channelEntity.getId())
                        .channelName(channelEntity.getChannelName())
                        .category(channelEntity.getCategory())
                        .isVoiceChannel(channelEntity.isVoiceChannel())
                        .isPrivate(channelEntity.isPrivate())
                        .userIdList(channelEntity.isPrivate() ? readStatusRepository.findByChannelId(
                                channelEntity.getId()).stream()
                                .map(ReadStatusEntity::getUserId).toList() : new ArrayList<>())
                        .recentMessageTime(messageRepository.findByChannelId(channelEntity.getId()).stream()
                                .max(Comparator.comparing(MessageEntity::getCreatedAt))
                                .map(MessageEntity::getCreatedAt).orElse(null))
                        .description(channelEntity.getDescription())
                        .createdAt(channelEntity.getCreatedAt())
                        .updatedAt(channelEntity.getUpdatedAt())
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

        ChannelEntity updatedChannelEntity = channelRepository.findById(request.id())
                .orElseThrow(() -> new NoSuchDataException("No such channel."));

        if (updatedChannelEntity.isPrivate()) {
            throw new IllegalArgumentException("Private channel cannot be updated.");
        }

        updatedChannelEntity.update(request.channelName(), request.category(), request.isVoiceChannel(), request.description());
        channelRepository.save(updatedChannelEntity);

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
