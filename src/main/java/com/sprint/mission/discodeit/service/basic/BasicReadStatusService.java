package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;
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
    public void createReadStatus(ReadStatusDTO.CreateReadStatusRequest request) {

        if (!userRepository.existById(request.userId())) {
            throw new IllegalArgumentException("No such user.");
        }

        if (!channelRepository.existById(request.channelId())) {
            throw new IllegalArgumentException("No such channel.");
        }

        if (existReadStatusByUserIdAndChannelId(request.userId(), request.channelId())) {
            throw new IllegalArgumentException("Read status already exists.");
        }

        ReadStatus readStatus = new ReadStatus.Builder()
                .channelId(request.channelId())
                .userId(request.userId())
                .build();

        readStatusRepository.save(readStatus);

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

        ReadStatus readStatus = readStatusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such read status."));

        return Optional.ofNullable(ReadStatusDTO.FindReadStatusResult.builder()
                .id(readStatus.getId())
                .channelId(readStatus.getChannelId())
                .userId(readStatus.getUserId())
                .lastReadTimestamp(readStatus.getLastReadTimestamp())
                .createdAt(readStatus.getCreatedAt())
                .updatedAt(readStatus.getUpdatedAt())
                .build());
    }

    @Override
    public Optional<ReadStatusDTO.FindReadStatusResult> findReadStatusByUserIdAndChannelId(UUID userId, UUID channelId) {

        if (!userRepository.existById(userId)) {
            throw new IllegalArgumentException("No such user.");
        }

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        ReadStatus readStatus = readStatusRepository.findByUserIdAndChannelId(userId, channelId)
                .orElseThrow(() -> new IllegalArgumentException("No such read status."));

        return Optional.ofNullable(ReadStatusDTO.FindReadStatusResult.builder()
                .id(readStatus.getId())
                .channelId(readStatus.getChannelId())
                .userId(readStatus.getUserId())
                .lastReadTimestamp(readStatus.getLastReadTimestamp())
                .createdAt(readStatus.getCreatedAt())
                .updatedAt(readStatus.getUpdatedAt())
                .build());

    }

    @Override
    public List<ReadStatusDTO.FindReadStatusResult> findAllReadStatusByUserId(UUID userId) {

        if (!userRepository.existById(userId)) {
            throw new IllegalArgumentException("No such user.");
        }

        return readStatusRepository.findByUserId(userId)
                .stream()
                .map(readStatus -> ReadStatusDTO.FindReadStatusResult.builder()
                        .id(readStatus.getId())
                        .channelId(readStatus.getChannelId())
                        .userId(readStatus.getUserId())
                        .lastReadTimestamp(readStatus.getLastReadTimestamp())
                        .createdAt(readStatus.getCreatedAt())
                        .updatedAt(readStatus.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public List<ReadStatusDTO.FindReadStatusResult> findAllReadStatusByChannelId(UUID channelId) {

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        return readStatusRepository.findByChannelId(channelId)
                .stream()
                .map(readStatus -> ReadStatusDTO.FindReadStatusResult.builder()
                        .id(readStatus.getId())
                        .channelId(readStatus.getChannelId())
                        .userId(readStatus.getUserId())
                        .lastReadTimestamp(readStatus.getLastReadTimestamp())
                        .createdAt(readStatus.getCreatedAt())
                        .updatedAt(readStatus.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public List<ReadStatusDTO.FindReadStatusResult> findAllReadStatus() {
        return readStatusRepository.findAll().stream()
                .map(readStatus -> ReadStatusDTO.FindReadStatusResult.builder()
                        .id(readStatus.getId())
                        .channelId(readStatus.getChannelId())
                        .userId(readStatus.getUserId())
                        .lastReadTimestamp(readStatus.getLastReadTimestamp())
                        .createdAt(readStatus.getCreatedAt())
                        .updatedAt(readStatus.getUpdatedAt())
                        .build())
                .toList();
    }

    @Override
    public void updateReadStatus(ReadStatusDTO.UpdateReadStatusRequest request) {

        ReadStatus readStatus = readStatusRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("No such read status."));

        readStatus.updateLastReadTimestamp();

        readStatusRepository.save(readStatus);

    }

    @Override
    public void deleteReadStatusById(UUID id) {

        if (!readStatusRepository.existById(id)) {
            throw new IllegalArgumentException("No such read status.");
        }

        readStatusRepository.deleteById(id);

    }

    @Override
    public void deleteReadStatusByUserIdAndChannelId(UUID userId, UUID channelId) {

        if (!userRepository.existById(userId)) {
            throw new IllegalArgumentException("No such user.");
        }

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        readStatusRepository.deleteByUserIdAndChannelId(userId, channelId);

    }

    @Override
    public void deleteAllReadStatusByUserId(UUID userId) {

        if (!userRepository.existById(userId)) {
            throw new IllegalArgumentException("No such user.");
        }

        readStatusRepository.deleteByUserId(userId);

    }

    @Override
    public void deleteAllReadStatusByChannelId(UUID channelId) {

        if (!channelRepository.existById(channelId)) {
            throw new IllegalArgumentException("No such channel.");
        }

        readStatusRepository.deleteByChannelId(channelId);

    }

    @Override
    public void deleteAllReadStatusByIdIn(List<UUID> uuidList) {

        uuidList.forEach(uuid -> {
            if (!readStatusRepository.existById(uuid)) {
                throw new IllegalArgumentException("No such read status.");
            }
        });

        readStatusRepository.deleteAllByIdIn(uuidList);

    }
}
