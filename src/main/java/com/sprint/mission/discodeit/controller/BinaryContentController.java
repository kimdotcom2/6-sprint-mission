package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.api.BinaryContentApiDTO;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BinaryContentController {

    private final BinaryContentService binaryContentService;

    @GetMapping("/api/binary-content/read")
    public ResponseEntity<BinaryContentApiDTO.ReadBinaryContentResponse> readBinaryContent(@RequestParam UUID id) {

        BinaryContentDTO.ReadBinaryContentResult readBinaryContentResult = binaryContentService.findBinaryContentById(id).get();

        return ResponseEntity.ok(BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                .id(id)
                .data(readBinaryContentResult.data())
                .fileType(readBinaryContentResult.fileType())
                .build());

    }

    @GetMapping("/api/binary-content/find-by-id-in")
    public List<BinaryContentApiDTO.ReadBinaryContentResponse> readBinaryContentsByIdIn(List<UUID> idList) {

        return binaryContentService.findAllBinaryContentByIdIn(idList).stream()
                .map(binaryContent -> BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                        .id(binaryContent.id())
                        .data(binaryContent.data())
                        .fileType(binaryContent.fileType())
                        .build())
                .toList();

    }

}
