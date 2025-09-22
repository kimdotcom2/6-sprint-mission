package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.dto.api.ReadStatusApiDTO;
import com.sprint.mission.discodeit.exception.AllReadyExistDataException;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@RestController("/api/readStatus")
@RequiredArgsConstructor
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @PostMapping()
    public ResponseEntity<ReadStatusApiDTO.FindReadStatusResponse> createReadStatus(@RequestBody ReadStatusApiDTO.ReadStatusCreateRequest readStatusCreateRequest) {

        readStatusService.createReadStatus(ReadStatusDTO.CreateReadStatusCommand.builder()
                .channelId(readStatusCreateRequest.channelId())
                .userId(readStatusCreateRequest.userId())
                .build());

        ReadStatusDTO.FindReadStatusResult readStatus = readStatusService.findReadStatusByUserIdAndChannelId(readStatusCreateRequest.userId(), readStatusCreateRequest.channelId())
                .orElseThrow(() -> new NoSuchDataException("No such read status."));

        return ResponseEntity.status(201).body(ReadStatusApiDTO.FindReadStatusResponse.builder()
                .id(readStatus.id())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(readStatus.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(readStatus.updatedAt()), ZoneId.systemDefault()))
                .channelId(readStatus.channelId())
                .userId(readStatus.userId())
                .lastReadAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(readStatus.lastReadTimestamp()), ZoneId.systemDefault()))
                .build());

    }

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<String> handleNoSuchDataException(NoSuchDataException e) {
        return ResponseEntity.status(404).body("Channel 또는 User를 찾을 수 없음");
    }

    @ExceptionHandler(AllReadyExistDataException.class)
    public ResponseEntity<String> handleAllReadyExistDataException(AllReadyExistDataException e) {
        return ResponseEntity.status(400).body("이미 읽음 상태가 존재함");
    }


    @PutMapping
    public ResponseEntity<String> updateReadStatus(@RequestBody ReadStatusApiDTO.UpdateReadStatusRequest updateReadStatusRequest) {

        readStatusService.updateReadStatus(ReadStatusDTO.UpdateReadStatusCommand.builder()
                .id(updateReadStatusRequest.id())
                .build());

        return ResponseEntity.ok("Read status updated successfully");

    }

    @GetMapping()
    public ResponseEntity<List<ReadStatusApiDTO.FindReadStatusResponse>> findAllReadStatusByUserId(@RequestParam UUID userId) {

        List<ReadStatusApiDTO.FindReadStatusResponse> readStatusList = readStatusService.findAllReadStatusByUserId(userId).stream()
                .map(readStatus -> ReadStatusApiDTO.FindReadStatusResponse.builder()
                .id(readStatus.id())
                        .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(readStatus.createdAt()), ZoneId.systemDefault()))
                        .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(readStatus.updatedAt()), ZoneId.systemDefault()))
                .channelId(readStatus.channelId())
                .userId(readStatus.userId())
                .lastReadAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(readStatus.lastReadTimestamp()), ZoneId.systemDefault()))
                .build())
                .toList();

        return ResponseEntity.ok(readStatusList);

    }

}
