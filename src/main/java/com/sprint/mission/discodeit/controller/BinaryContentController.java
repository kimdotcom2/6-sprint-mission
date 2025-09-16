package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.api.BinaryContentApiDTO;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BinaryContentController {

    private final BinaryContentService binaryContentService;

    @RequestMapping(value = "/api/binary-content/read", method = RequestMethod.GET)
    public ResponseEntity<BinaryContentApiDTO.ReadBinaryContentResponse> readBinaryContent(@RequestParam(value = "id") UUID id) {

        BinaryContentDTO.ReadBinaryContentResult readBinaryContentResult = binaryContentService.findBinaryContentById(id).get();

        return ResponseEntity.ok(BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                .id(id)
                .data(readBinaryContentResult.data())
                .fileType(readBinaryContentResult.fileType())
                .createdAt(readBinaryContentResult.createdAt())
                .build());

    }

    @RequestMapping(value = "/api/binary-content/read-by-id-in", method = RequestMethod.GET)
    public List<BinaryContentApiDTO.ReadBinaryContentResponse> readBinaryContentsByIdIn(List<UUID> idList) {

        return binaryContentService.findAllBinaryContentByIdIn(idList).stream()
                .map(binaryContent -> BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                        .id(binaryContent.id())
                        .data(binaryContent.data())
                        .fileType(binaryContent.fileType())
                        .createdAt(binaryContent.createdAt())
                        .build())
                .toList();

    }

}
