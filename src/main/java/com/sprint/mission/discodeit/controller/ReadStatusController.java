package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.dto.api.ReadStatusApiDTO;
import com.sprint.mission.discodeit.dto.api.ReadStatusApiDTO.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.exception.AllReadyExistDataException;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/readStatus")
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

    @PatchMapping("/{readStatusId}")
    public ResponseEntity<ReadStatusApiDTO.FindReadStatusResponse> updateReadStatus(@PathVariable UUID readStatusId, @RequestBody ReadStatusUpdateRequest readStatusUpdateRequest) {

        readStatusService.updateReadStatus(ReadStatusDTO.UpdateReadStatusCommand.builder()
                .id(readStatusId)
                .lastReadTimestamp(readStatusUpdateRequest.newLastReadAt().toEpochSecond(ZoneOffset.UTC))
                .build());

        ReadStatusDTO.FindReadStatusResult readStatus = readStatusService.findReadStatusById(readStatusId)
                .orElseThrow(() -> new NoSuchDataException("No such read status."));

        return ResponseEntity.ok(ReadStatusApiDTO.FindReadStatusResponse.builder()
                .id(readStatus.id())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(readStatus.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(readStatus.updatedAt()), ZoneId.systemDefault()))
                .channelId(readStatus.channelId())
                .userId(readStatus.userId())
                .lastReadAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(readStatus.lastReadTimestamp()), ZoneId.systemDefault()))
                .build());

    }

    @GetMapping("/user")
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

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleNoSuchDataException(NoSuchDataException e) {
        return ResponseEntity.status(404).body(ErrorApiDTO.ErrorApiResponse.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .message(e.getMessage())
            .build());
    }

    @ExceptionHandler(AllReadyExistDataException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleAllReadyExistDataException(AllReadyExistDataException e) {
        return ResponseEntity.status(400).body(ErrorApiDTO.ErrorApiResponse.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message(e.getMessage())
            .build());
    }

}
