package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.api.BinaryContentApiDTO;
import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.service.BinaryContentService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/binaryContents")
@RequiredArgsConstructor
public class BinaryContentController {

    private final BinaryContentService binaryContentService;

    @GetMapping("/{binaryContentId}")
    public ResponseEntity<BinaryContentApiDTO.ReadBinaryContentResponse> readBinaryContent(@RequestParam("binaryContentId") UUID id) {

        BinaryContentDTO.ReadBinaryContentResult readBinaryContentResult = binaryContentService.findBinaryContentById(id)
            .orElseThrow(() -> new NoSuchDataException("BinaryContent not found"));

        return ResponseEntity.ok(BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                .id(readBinaryContentResult.id())
                .fileName(readBinaryContentResult.fileName())
                .size(readBinaryContentResult.size())
                .data(readBinaryContentResult.data())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(readBinaryContentResult.createdAt()), ZoneId.systemDefault()))
                .fileType(readBinaryContentResult.fileType())
                .build());

    }

    @GetMapping()
    public List<BinaryContentApiDTO.ReadBinaryContentResponse> readBinaryContentsByIdIn(@RequestParam("binaryContentIds") List<UUID> idList) {

        return binaryContentService.findAllBinaryContentByIdIn(idList).stream()
                .map(binaryContent -> BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                        .id(binaryContent.id())
                        .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(binaryContent.createdAt()), ZoneId.systemDefault()))
                        .fileName(binaryContent.fileName())
                        .size(binaryContent.size())
                        .data(binaryContent.data())
                        .fileType(binaryContent.fileType())
                        .build())
                .toList();

    }

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleNoSuchDataException(NoSuchDataException e) {
        return ResponseEntity.status(404).body(ErrorApiDTO.ErrorApiResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(ErrorApiDTO.ErrorApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build());
    }

}
