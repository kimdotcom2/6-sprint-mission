package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadStatusService {

    void createReadStatus(ReadStatusDTO.CreateReadStatusRequest request);

    boolean existReadStatusById(UUID id);

    boolean existReadStatusByUserIdAndChannelId(UUID userId, UUID channelId);

    Optional<ReadStatusDTO.FindReadStatusResult> findReadStatusById(UUID id);

    Optional<ReadStatusDTO.FindReadStatusResult> findReadStatusByUserIdAndChannelId(UUID userId, UUID channelId);

    List<ReadStatusDTO.FindReadStatusResult> findAllReadStatusByUserId(UUID userId);

    List<ReadStatusDTO.FindReadStatusResult> findAllReadStatusByChannelId(UUID channelId);

    List<ReadStatusDTO.FindReadStatusResult> findAllReadStatus();

    void updateReadStatus(ReadStatusDTO.UpdateReadStatusRequest request);

    void deleteReadStatusById(UUID id);

    void deleteReadStatusByUserIdAndChannelId(UUID userId, UUID channelId);

    void deleteAllReadStatusByUserId(UUID userId);

    void deleteAllReadStatusByChannelId(UUID channelId);

    void deleteAllReadStatusByIdIn(List<UUID> uuidList);

}
