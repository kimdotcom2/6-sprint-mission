package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadStatusService {

  ReadStatusDTO.ReadStatus createReadStatus(ReadStatusDTO.CreateReadStatusCommand request);

  boolean existReadStatusById(UUID id);

  boolean existReadStatusByUserIdAndChannelId(UUID userId, UUID channelId);

  Optional<ReadStatusDTO.ReadStatus> findReadStatusById(UUID id);

  Optional<ReadStatusDTO.ReadStatus> findReadStatusByUserIdAndChannelId(UUID userId, UUID channelId);

  List<ReadStatusDTO.ReadStatus> findAllReadStatusByUserId(UUID userId);

  List<ReadStatusDTO.ReadStatus> findAllReadStatusByChannelId(UUID channelId);

  List<ReadStatusDTO.ReadStatus> findAllReadStatus();

  ReadStatusDTO.ReadStatus updateReadStatus(ReadStatusDTO.UpdateReadStatusCommand request);

  void deleteReadStatusById(UUID id);

  void deleteReadStatusByUserIdAndChannelId(UUID userId, UUID channelId);

  void deleteAllReadStatusByUserId(UUID userId);

  void deleteAllReadStatusByChannelId(UUID channelId);

  void deleteAllReadStatusByIdIn(List<UUID> uuidList);

}
