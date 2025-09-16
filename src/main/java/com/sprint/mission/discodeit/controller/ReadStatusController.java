package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.dto.api.ReadStatusApiDTO;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @RequestMapping(value = "/api/read-status/create", method = RequestMethod.POST)
    public ResponseEntity<String> createReadStatus(@RequestBody ReadStatusApiDTO.CreateReadStatusRequest createReadStatusRequest) {

        readStatusService.createReadStatus(ReadStatusDTO.CreateReadStatusCommand.builder()
                .channelId(createReadStatusRequest.channelId())
                .userId(createReadStatusRequest.userId())
                .build());

        return ResponseEntity.ok("Read status created successfully");

    }

    @RequestMapping(value = "/api/read-status/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateReadStatus(@RequestBody ReadStatusApiDTO.UpdateReadStatusRequest updateReadStatusRequest) {

        readStatusService.updateReadStatus(ReadStatusDTO.UpdateReadStatusCommand.builder()
                .id(updateReadStatusRequest.id())
                .build());

        return ResponseEntity.ok("Read status updated successfully");

    }

    @RequestMapping(value = "/api/read-status/{userId}", method = RequestMethod.GET)
    public List<ReadStatusApiDTO.FindReadStatusResponse> findReadStatusByUserId(@PathVariable UUID userId) {

        return readStatusService.findAllReadStatusByUserId(userId).stream()
                .map(readStatus -> ReadStatusApiDTO.FindReadStatusResponse.builder()
                .id(readStatus.id())
                .channelId(readStatus.channelId())
                .userId(readStatus.userId())
                .lastReadTimestamp(readStatus.lastReadTimestamp()).build())
                .toList();

    }

}
