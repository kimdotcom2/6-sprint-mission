package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatusEntity;
import com.sprint.mission.discodeit.exception.AllReadyExistDataException;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {

    private  final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    @Override
    public void createReadStatus(ReadStatusDTO.CreateReadStatusCommand request) {

        if (!userRepository.existById(request.userId())) {
            throw new NoSuchDataException("No such user.");
        }

        if (!channelRepository.existById(request.channelId())) {
            throw new NoSuchDataException("No such channel.");
        }

        if (existReadStatusByUserIdAndChannelId(request.userId(), request.channelId())) {
            throw new AllReadyExistDataException("Read status already exists.");
        }

        ReadStatusEntity readStatusEntity = new ReadStatusEntity.Builder()
                .channelId(request.channelId())
                .userId(request.userId())
                .lastReadTimestamp(request.lastReadTimestamp())
                .build();

        readStatusRepository.save(readStatusEntity);

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
    public Optional<ReadStatusDTO.FindReadStatusResult> findReadStatusById(UUID id) {

        ReadStatusEntity readStatusEntity = readStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("No such read status."));

        return Optional.ofNullable(ReadStatusDTO.FindReadStatusResult.builder()
                .id(readStatusEntity.getId())
                .channelId(readStatusEntity.getChannelId())
                .userId(readStatusEntity.getUserId())
                .lastReadTimestamp(readStatusEntity.getLastReadTimestamp())
                .createdAt(readStatusEntity.getCreatedAt())
                .updatedAt(readStatusEntity.getUpdatedAt())
                .build());
    }

    @Override
    public Optional<ReadStatusDTO.FindReadStatusResult> findReadStatusByUserIdAndChannelId(UUID userId, UUID channelId) {

        if (!userRepository.existById(userId)) {
            throw new NoSuchDataException("No such user.");
        }

        if (!channelRepository.existById(channelId)) {
            throw new NoSuchDataException("No such channel.");
        }

        ReadStatusEntity readStatusEntity = readStatusRepository.findByUserIdAndChannelId(userId, channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such read status."));

        return Optional.ofNullable(ReadStatusDTO.FindReadStatusResult.builder()
                .id(readStatusEntity.getId())
                .channelId(readStatusEntity.getChannelId())
                .userId(readStatusEntity.getUserId())
                .lastReadTimestamp(readStatusEntity.getLastReadTimestamp())
                .createdAt(readStatusEntity.getCreatedAt())
                .updatedAt(readStatusEntity.getUpdatedAt())
                .build());

    }

    @Override
    public List<ReadStatusDTO.FindReadStatusResult> findAllReadStatusByUserId(UUID userId) {

        if (!userRepository.existById(userId)) {
            throw new NoSuchDataException("No such user.");
        }

        return readStatusRepository.findByUserId(userId)
                .stream()
                .map(readStatusEntity -> ReadStatusDTO.FindReadStatusResult.builder()
                        .id(readStatusEntity.getId())
                        .channelId(readStatusEntity.getChannelId())
                        .userId(readStatusEntity.getUserId())
                        .lastReadTimestamp(readStatusEntity.getLastReadTimestamp())
                        .createdAt(readStatusEntity.getCreatedAt())
                        .updatedAt(readStatusEntity.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public List<ReadStatusDTO.FindReadStatusResult> findAllReadStatusByChannelId(UUID channelId) {

        if (!channelRepository.existById(channelId)) {
            throw new NoSuchDataException("No such channel.");
        }

        return readStatusRepository.findByChannelId(channelId)
                .stream()
                .map(readStatusEntity -> ReadStatusDTO.FindReadStatusResult.builder()
                        .id(readStatusEntity.getId())
                        .channelId(readStatusEntity.getChannelId())
                        .userId(readStatusEntity.getUserId())
                        .lastReadTimestamp(readStatusEntity.getLastReadTimestamp())
                        .createdAt(readStatusEntity.getCreatedAt())
                        .updatedAt(readStatusEntity.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public List<ReadStatusDTO.FindReadStatusResult> findAllReadStatus() {
        return readStatusRepository.findAll().stream()
                .map(readStatusEntity -> ReadStatusDTO.FindReadStatusResult.builder()
                        .id(readStatusEntity.getId())
                        .channelId(readStatusEntity.getChannelId())
                        .userId(readStatusEntity.getUserId())
                        .lastReadTimestamp(readStatusEntity.getLastReadTimestamp())
                        .createdAt(readStatusEntity.getCreatedAt())
                        .updatedAt(readStatusEntity.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public void updateReadStatus(ReadStatusDTO.UpdateReadStatusCommand request) {

        ReadStatusEntity readStatusEntity = readStatusRepository.findById(request.id())
                .orElseThrow(() -> new NoSuchDataException("No such read status."));

        readStatusEntity.updateLastReadTimestamp(request.lastReadTimestamp());

        readStatusRepository.save(readStatusEntity);

    }

    @Override
    public void deleteReadStatusById(UUID id) {

        if (!readStatusRepository.existById(id)) {
            throw new NoSuchDataException("No such read status.");
        }

        readStatusRepository.deleteById(id);

    }

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

    @Override
    public void deleteAllReadStatusByUserId(UUID userId) {

        if (!userRepository.existById(userId)) {
            throw new NoSuchDataException("No such user.");
        }

        readStatusRepository.deleteByUserId(userId);

    }

    @Override
    public void deleteAllReadStatusByChannelId(UUID channelId) {

        if (!channelRepository.existById(channelId)) {
            throw new NoSuchDataException("No such channel.");
        }

        readStatusRepository.deleteByChannelId(channelId);

    }

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
