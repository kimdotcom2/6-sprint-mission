package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatusEntity;
import com.sprint.mission.discodeit.exception.AllReadyExistDataException;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.mapper.ReadStatusEntityMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

        if (!userRepository.existById(request.userId())) {
            throw new NoSuchDataException("No such user.");
        }

        if (!channelRepository.existById(request.channelId())) {
            throw new NoSuchDataException("No such channel.");
        }

        if (existReadStatusByUserIdAndChannelId(request.userId(), request.channelId())) {
            throw new AllReadyExistDataException("Read status already exists.");
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
        return readStatusRepository.existById(id);
    }

    @Override
    public boolean existReadStatusByUserIdAndChannelId(UUID userId, UUID channelId) {
        return readStatusRepository.existByUserIdAndChannelId(userId, channelId);
    }

    @Override
    public Optional<ReadStatusDTO.ReadStatus> findReadStatusById(UUID id) {

        ReadStatusEntity readStatusEntity = readStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("No such read status."));

        return Optional.ofNullable(readStatusEntityMapper.entityToReadStatus(readStatusEntity));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ReadStatusDTO.ReadStatus> findReadStatusByUserIdAndChannelId(UUID userId, UUID channelId) {

        if (!userRepository.existById(userId)) {
            throw new NoSuchDataException("No such user.");
        }

        if (!channelRepository.existById(channelId)) {
            throw new NoSuchDataException("No such channel.");
        }

        ReadStatusEntity readStatusEntity = readStatusRepository.findByUserIdAndChannelId(userId, channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such read status."));

        return Optional.ofNullable(readStatusEntityMapper.entityToReadStatus(readStatusEntity));

    }

    @Override
    public List<ReadStatusDTO.ReadStatus> findAllReadStatusByUserId(UUID userId) {

        if (!userRepository.existById(userId)) {
            throw new NoSuchDataException("No such user.");
        }

        return readStatusRepository.findByUserId(userId)
                .stream()
                .map(readStatusEntityMapper::entityToReadStatus)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReadStatusDTO.ReadStatus> findAllReadStatusByChannelId(UUID channelId) {

        if (!channelRepository.existById(channelId)) {
            throw new NoSuchDataException("No such channel.");
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
    public void updateReadStatus(ReadStatusDTO.UpdateReadStatusCommand request) {

        ReadStatusEntity readStatusEntity = readStatusRepository.findById(request.id())
                .orElseThrow(() -> new NoSuchDataException("No such read status."));

        readStatusEntity.updateLastReadAt(request.lastReadAt());

        readStatusRepository.save(readStatusEntity);

    }

    @Transactional
    @Override
    public void deleteReadStatusById(UUID id) {

        if (!readStatusRepository.existById(id)) {
            throw new NoSuchDataException("No such read status.");
        }

        readStatusRepository.deleteById(id);

    }

    @Transactional
    @Override
    public void deleteReadStatusByUserIdAndChannelId(UUID userId, UUID channelId) {

        if (!userRepository.existById(userId)) {
            throw new NoSuchDataException("No such user.");
        }

        if (!channelRepository.existById(channelId)) {
            throw new NoSuchDataException("No such channel.");
        }

        readStatusRepository.deleteByUserIdAndChannelId(userId, channelId);

    }

    @Transactional
    @Override
    public void deleteAllReadStatusByUserId(UUID userId) {

        if (!userRepository.existById(userId)) {
            throw new NoSuchDataException("No such user.");
        }

        readStatusRepository.deleteByUserId(userId);

    }

    @Transactional
    @Override
    public void deleteAllReadStatusByChannelId(UUID channelId) {

        if (!channelRepository.existById(channelId)) {
            throw new NoSuchDataException("No such channel.");
        }

        readStatusRepository.deleteByChannelId(channelId);

    }

    @Transactional
    @Override
    public void deleteAllReadStatusByIdIn(List<UUID> uuidList) {

        uuidList.forEach(uuid -> {
            if (!readStatusRepository.existById(uuid)) {
                throw new NoSuchDataException("No such read status.");
            }
        });

        readStatusRepository.deleteAllByIdIn(uuidList);

    }
}
