package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelDTO;
import com.sprint.mission.discodeit.entity.ChannelEntity;
import com.sprint.mission.discodeit.entity.ReadStatusEntity;
import com.sprint.mission.discodeit.entity.UserEntity;
import com.sprint.mission.discodeit.enums.ChannelType;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import com.sprint.mission.discodeit.mapper.ChannelEntityMapper;
import com.sprint.mission.discodeit.mapper.UserEntityMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  private final MessageRepository messageRepository;
  private final ReadStatusRepository readStatusRepository;
  private final UserRepository userRepository;
  private final ChannelEntityMapper channelEntityMapper;
  private final UserEntityMapper userEntityMapper;
  private final BasicChannelService.ChannelWithParticipants channelWithParticipants;

  @Transactional
  @Override
  public ChannelDTO.Channel createChannel(ChannelDTO.CreatePublicChannelCommand request) {

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

    ChannelDTO.Channel channel = channelEntityMapper.entityToChannel(channelRepository.save(channelEntity));

    List<ReadStatusEntity> readStatusEntityList = request.participants().stream()
        .map(userId -> ReadStatusEntity.builder()
            .user(userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchDataBaseRecordException("No such user.")))
            .channel(channelEntity)
            .lastReadAt(Instant.now())
            .build())
        .toList();

    readStatusRepository.saveAll(readStatusEntityList);

    channel.addParticipants(readStatusEntityList.stream()
        .map(ReadStatusEntity::getUser)
        .filter(Objects::nonNull)
        .map(userEntityMapper::entityToUser)
        .toList());

    return channel;

  }

  @Override
  public boolean existChannelById(UUID id) {
    return channelRepository.existsById(id);
  }

  @Override
  public Optional<ChannelDTO.Channel> findChannelById(UUID id) {

    ChannelEntity channelEntity = channelRepository.findById(id)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such channel."));

    ChannelDTO.Channel channel = channelWithParticipants.addParticipantsToChannel(channelEntity);

    return Optional.ofNullable(channel);

  }

  @Transactional(readOnly = true)
  @Override
  public List<ChannelDTO.Channel> findChannelsByUserId(UUID userId) {


    List<ChannelDTO.Channel> channelList = new ArrayList<>(readStatusRepository.findByUserId(userId).stream()
        .map(ReadStatusEntity::getChannel)
        .map(channelWithParticipants::addParticipantsToChannel)
        .toList());

    channelList.addAll(channelRepository.findByType(ChannelType.PUBLIC).stream()
        .map(channelEntityMapper::entityToChannel)
        .toList());

    return channelList;

  }

  @Override
  public List<ChannelDTO.Channel> findAllChannels() {
    return channelRepository.findAll().stream()
        .map(channelWithParticipants::addParticipantsToChannel)
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

  @RequiredArgsConstructor
  @Component
  static class ChannelWithParticipants {

    private final ChannelEntityMapper channelEntityMapper;
    private final UserEntityMapper userEntityMapper;
    private final ReadStatusRepository readStatusRepository;

    private ChannelDTO.Channel addParticipantsToChannel(ChannelEntity channelEntity) {

      ChannelDTO.Channel channel = channelEntityMapper.entityToChannel(channelEntity);

      if (channelEntity.getType() == ChannelType.PUBLIC) {
        return channel;
      }

      List<ReadStatusEntity> readStatusEntityList = readStatusRepository.findByChannelId(channelEntity.getId());

      channel.addParticipants(readStatusEntityList.stream()
          .map(ReadStatusEntity::getUser)
          .filter(Objects::nonNull)
          .map(userEntityMapper::entityToUser)
          .toList());

      return channel;

    }

  }



}
