package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.entity.ChannelEntity;
import com.sprint.mission.discodeit.entity.ReadStatusEntity;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import com.sprint.mission.discodeit.mapper.ChannelEntityMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  private final MessageRepository messageRepository;
  private final ReadStatusRepository readStatusRepository;
  private ChannelEntityMapper channelEntityMapper;

  @Transactional
  @Override
  public ChannelDTO.Channel createChannel(ChannelDTO.CreatePublicChannelCommand request) {

    if (request.name().isBlank()) {
      throw new IllegalArgumentException("Invalid channel data.");
    }

    ChannelEntity channelEntity = ChannelEntity.builder()
        .type(ChannelType.PUBLIC)
        .name(request.name())
        .description(request.description())
        .build();

    return channelEntityMapper.entityToChannel(channelRepository.save(channelEntity));

  }

  @Transactional
  @Override
  public ChannelDTO.Channel createPrivateChannel(ChannelDTO.CreatePrivateChannelCommand request) {

    ChannelEntity channelEntity = ChannelEntity.builder()
        .type(request.type())
        .description(request.description())
        .build();

    List<ReadStatusEntity> readStatusEntityList = request.participants().stream()
        .map(userId -> ReadStatusEntity.builder()
            .lastReadAt(Instant.now())
            .build())
        .toList();

    readStatusRepository.saveAll(readStatusEntityList);
    channelRepository.save(channelEntity);

    return channelEntityMapper.entityToChannel(channelEntity);

  }

  @Override
  public boolean existChannelById(UUID id) {
    return channelRepository.existsById(id);
  }

  @Override
  public Optional<ChannelDTO.Channel> findChannelById(UUID id) {

    ChannelEntity channelEntity = channelRepository.findById(id)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such channel."));

    return Optional.ofNullable(channelEntityMapper.entityToChannel(channelEntity));

  }

  @Transactional(readOnly = true)
  @Override
  public List<ChannelDTO.Channel> findChannelsByUserId(UUID userId) {

    List<ChannelDTO.Channel> privateChannelList = readStatusRepository.findByUserId(userId).stream()
        .map(ReadStatusEntity::getChannel)
        .map(channelEntityMapper::entityToChannel)
        .toList();

    List<ChannelDTO.Channel> publicChannelList = channelRepository.findAll().stream()
        .filter(channelEntity -> channelEntity.getType() == ChannelType.PUBLIC)
        .map(channelEntityMapper::entityToChannel)
        .toList();

    Set<ChannelDTO.Channel> combined = new HashSet<>();
    combined.addAll(privateChannelList);
    combined.addAll(publicChannelList);

    return new ArrayList<>(combined);
  }

  @Override
  public List<ChannelDTO.Channel> findAllChannels() {
    return channelRepository.findAll().stream()
        .map(channelEntityMapper::entityToChannel)
        .toList();
  }

  @Transactional
  @Override
  public ChannelDTO.Channel updateChannel(ChannelDTO.UpdateChannelCommand request) {

    if (!channelRepository.existsById(request.id())) {
      throw new NoSuchDataBaseRecordException("No such channel.");
    }

    if (request.name().isBlank() || request.type() == null) {
      throw new IllegalArgumentException("Invalid channel data.");
    }

    ChannelEntity updatedChannelEntity = channelRepository.findById(request.id())
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such channel."));

    if (updatedChannelEntity.isPrivate()) {
      throw new IllegalArgumentException("Private channel cannot be updated.");
    }

    updatedChannelEntity.update(request.name(), request.description());

    return channelEntityMapper.entityToChannel(channelRepository.save(updatedChannelEntity));

  }

  @Transactional
  @Override
  public void deleteChannelById(UUID id) {

    if (!channelRepository.existsById(id)) {
      throw new NoSuchDataBaseRecordException("No such channel.");
    }

    messageRepository.deleteByChannelId(id);
    readStatusRepository.deleteByChannelId(id);
    channelRepository.deleteById(id);

  }

}
