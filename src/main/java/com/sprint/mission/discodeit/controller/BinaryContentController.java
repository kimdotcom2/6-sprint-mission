package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.api.BinaryContentApiDTO;
import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.service.BinaryContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 바이너리 콘텐츠 관련 API 컨트롤러
 */
@Tag(name = "바이너리 콘텐츠 API", description = "바이너리 파일 업로드 및 관리를 위한 API")
@RestController
@Slf4j
@RequestMapping("/api/binaryContents")
@RequiredArgsConstructor
public class BinaryContentController {

    private final BinaryContentService binaryContentService;

    /**
     * 바이너리 콘텐츠 조회
     *
     * @param id 바이너리 콘텐츠 ID
     * @return 바이너리 콘텐츠 정보
     */
    @Operation(
        summary = "바이너리 콘텐츠 조회",
        description = "지정된 ID의 바이너리 콘텐츠를 조회합니다.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "바이너리 콘텐츠 조회 성공",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = BinaryContentApiDTO.ReadBinaryContentResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "바이너리 콘텐츠를 찾을 수 없음",
                content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
            )
        }
    )
    @GetMapping("/{binaryContentId}")
    public ResponseEntity<BinaryContentApiDTO.ReadBinaryContentResponse> readBinaryContent(
            @Parameter(description = "바이너리 콘텐츠 ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable("binaryContentId") UUID id) {

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

    /**
     * 다중 바이너리 콘텐츠 조회
     *
     * @param idList 바이너리 콘텐츠 ID 목록
     * @return 바이너리 콘텐츠 목록
     */
    @Operation(
        summary = "다중 바이너리 콘텐츠 조회",
        description = "지정된 ID 목록에 해당하는 바이너리 콘텐츠 목록을 조회합니다.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "바이너리 콘텐츠 목록 조회 성공",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = BinaryContentApiDTO.ReadBinaryContentResponse.class))
                )
            )
        }
    )
    @GetMapping()
    public ResponseEntity<List<BinaryContentApiDTO.ReadBinaryContentResponse>> readBinaryContentsByIdIn(
            @Parameter(
                description = "바이너리 콘텐츠 ID 목록",
                required = true,
                example = "123e4567-e89b-12d3-a456-426614174000,223e4567-e89b-12d3-a456-426614174001"
            )
            @RequestParam("binaryContentIds") List<UUID> idList) {

        return ResponseEntity.ok(binaryContentService.findAllBinaryContentByIdIn(idList).stream()
                .map(binaryContent -> BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                        .id(binaryContent.id())
                        .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(binaryContent.createdAt()), ZoneId.systemDefault()))
                        .fileName(binaryContent.fileName())
                        .size(binaryContent.size())
                        .data(binaryContent.data())
                        .fileType(binaryContent.fileType())
                        .build())
                .toList());

    }

    @ExceptionHandler(NoSuchDataException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleNoSuchDataException(NoSuchDataException e) {

      log.error("NoSuchDataException occurred", e);

      return ResponseEntity.status(404).body(ErrorApiDTO.ErrorApiResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build());
    }

    /**
     * 잘못된 인자 예외 처리
     *
     * @param e 발생한 예외
     * @return 에러 응답
     */
    @io.swagger.v3.oas.annotations.Hidden
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {

      log.error("IllegalArgumentException occurred", e);

      return ResponseEntity.status(400).body(ErrorApiDTO.ErrorApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build());
    }

}
