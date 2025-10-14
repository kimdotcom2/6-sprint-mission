package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatusEntity;
import com.sprint.mission.discodeit.exception.AllReadyExistDataBaseRecordException;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import com.sprint.mission.discodeit.mapper.ReadStatusEntityMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {

  private final ReadStatusRepository readStatusRepository;
  private final UserRepository userRepository;
  private final ChannelRepository channelRepository;
  private final ReadStatusEntityMapper readStatusEntityMapper;

  @Transactional
  @Override
  public ReadStatusDTO.ReadStatus createReadStatus(ReadStatusDTO.CreateReadStatusCommand request) {

    if (!userRepository.existsById(request.userId())) {
      throw new NoSuchDataBaseRecordException("No such user.");
    }

    if (!channelRepository.existsById(request.channelId())) {
      throw new NoSuchDataBaseRecordException("No such channel.");
    }

    if (existReadStatusByUserIdAndChannelId(request.userId(), request.channelId())) {
      throw new AllReadyExistDataBaseRecordException("Read status already exists.");
    }

    ReadStatusEntity readStatusEntity = ReadStatusEntity.builder()
        .user(userRepository.findById(request.userId()).get())
        .channel(channelRepository.findById(request.channelId()).get())
        .lastReadAt(request.lastReadTimeAt())
        .build();

    return readStatusEntityMapper.entityToReadStatus(readStatusRepository.save(readStatusEntity));

  }

  @Override
  public boolean existReadStatusById(UUID id) {
    return readStatusRepository.existsById(id);
  }

  @Override
  public boolean existReadStatusByUserIdAndChannelId(UUID userId, UUID channelId) {
    return readStatusRepository.existsByUserIdAndChannelId(userId, channelId);
  }

  @Override
  public Optional<ReadStatusDTO.ReadStatus> findReadStatusById(UUID id) {

    ReadStatusEntity readStatusEntity = readStatusRepository.findById(id)
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such read status."));

    return Optional.ofNullable(readStatusEntityMapper.entityToReadStatus(readStatusEntity));
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<ReadStatusDTO.ReadStatus> findReadStatusByUserIdAndChannelId(UUID userId,
      UUID channelId) {

    if (!userRepository.existsById(userId)) {
      throw new NoSuchDataBaseRecordException("No such user.");
    }

    if (!channelRepository.existsById(channelId)) {
      throw new NoSuchDataBaseRecordException("No such channel.");
    }

    ReadStatusEntity readStatusEntity = readStatusRepository.findByUserIdAndChannelId(userId,
            channelId)
        .orElseThrow(() -> new IllegalArgumentException("No such read status."));

    return Optional.ofNullable(readStatusEntityMapper.entityToReadStatus(readStatusEntity));

  }

  @Override
  public List<ReadStatusDTO.ReadStatus> findAllReadStatusByUserId(UUID userId) {

    if (!userRepository.existsById(userId)) {
      throw new NoSuchDataBaseRecordException("No such user.");
    }

    return readStatusRepository.findByUserId(userId)
        .stream()
        .map(readStatusEntityMapper::entityToReadStatus)
        .toList();
  }

  @Transactional(readOnly = true)
  @Override
  public List<ReadStatusDTO.ReadStatus> findAllReadStatusByChannelId(UUID channelId) {

    if (!channelRepository.existsById(channelId)) {
      throw new NoSuchDataBaseRecordException("No such channel.");
    }

    return readStatusRepository.findByChannelId(channelId)
        .stream()
        .map(readStatusEntityMapper::entityToReadStatus)
        .toList();
  }

  @Override
  public List<ReadStatusDTO.ReadStatus> findAllReadStatus() {
    return readStatusRepository.findAll().stream()
        .map(readStatusEntityMapper::entityToReadStatus)
        .toList();
  }

  @Transactional
  @Override
  public ReadStatusDTO.ReadStatus updateReadStatus(ReadStatusDTO.UpdateReadStatusCommand request) {

    ReadStatusEntity readStatusEntity = readStatusRepository.findById(request.id())
        .orElseThrow(() -> new NoSuchDataBaseRecordException("No such read status."));

    readStatusEntity.updateLastReadAt(request.lastReadAt());

    return readStatusEntityMapper.entityToReadStatus(readStatusRepository.save(readStatusEntity));

  }

  @Transactional
  @Override
  public void deleteReadStatusById(UUID id) {

    if (!readStatusRepository.existsById(id)) {
      throw new NoSuchDataBaseRecordException("No such read status.");
    }

    readStatusRepository.deleteById(id);

  }

  @Transactional
  @Override
  public void deleteReadStatusByUserIdAndChannelId(UUID userId, UUID channelId) {

    if (!userRepository.existsById(userId)) {
      throw new NoSuchDataBaseRecordException("No such user.");
    }

    if (!channelRepository.existsById(channelId)) {
      throw new NoSuchDataBaseRecordException("No such channel.");
    }

    readStatusRepository.deleteByUserIdAndChannelId(userId, channelId);

  }

  @Transactional
  @Override
  public void deleteAllReadStatusByUserId(UUID userId) {

    if (!userRepository.existsById(userId)) {
      throw new NoSuchDataBaseRecordException("No such user.");
    }

    readStatusRepository.deleteByUserId(userId);

  }

  @Transactional
  @Override
  public void deleteAllReadStatusByChannelId(UUID channelId) {

    if (!channelRepository.existsById(channelId)) {
      throw new NoSuchDataBaseRecordException("No such channel.");
    }

    readStatusRepository.deleteByChannelId(channelId);

  }

  @Transactional
  @Override
  public void deleteAllReadStatusByIdIn(List<UUID> uuidList) {

    uuidList.forEach(uuid -> {
      if (!readStatusRepository.existsById(uuid)) {
        throw new NoSuchDataBaseRecordException("No such read status.");
      }
    });

    readStatusRepository.deleteAllByIdIn(uuidList);

  }
}
