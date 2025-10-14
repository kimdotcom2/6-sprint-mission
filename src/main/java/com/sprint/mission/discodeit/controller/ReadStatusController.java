package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ReadStatusDTO;
import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.dto.api.ReadStatusApiDTO;
import com.sprint.mission.discodeit.dto.api.ReadStatusApiDTO.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.exception.AllReadyExistDataBaseRecordException;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import com.sprint.mission.discodeit.service.ReadStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 읽음 상태 관련 API 컨트롤러
 */
@Tag(name = "읽음 상태 API", description = "사용자의 메시지 읽음 상태 관리를 위한 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/readStatuses")
public class ReadStatusController {

  private final ReadStatusService readStatusService;

  /**
   * 읽음 상태 생성
   *
   * @param readStatusCreateRequest 읽음 상태 생성 요청 정보
   * @return 생성된 읽음 상태 정보
   */
  @Operation(
      summary = "읽음 상태 생성",
      description = "사용자의 채널별 읽음 상태를 생성합니다.",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "읽음 상태 생성 성공",
              content = @Content(schema = @Schema(implementation = ReadStatusApiDTO.FindReadStatusResponse.class))
          ),
          @ApiResponse(
              responseCode = "400",
              description = "이미 존재하는 읽음 상태",
              content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
          )
      }
  )
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReadStatusApiDTO.FindReadStatusResponse> createReadStatus(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "읽음 상태 생성 요청 정보",
          required = true,
          content = @Content(schema = @Schema(implementation = ReadStatusApiDTO.ReadStatusCreateRequest.class))
      )
      @RequestBody @Valid ReadStatusApiDTO.ReadStatusCreateRequest readStatusCreateRequest) {

    ReadStatusDTO.ReadStatus readStatus = readStatusService.createReadStatus(ReadStatusDTO.CreateReadStatusCommand.builder()
        .channelId(readStatusCreateRequest.channelId())
        .userId(readStatusCreateRequest.userId())
        .lastReadTimeAt(readStatusCreateRequest.lastReadAt())
        .build());

    return ResponseEntity.status(201).body(ReadStatusApiDTO.FindReadStatusResponse.builder()
        .channelId(readStatus.getChannelId())
        .userId(readStatus.getUserId())
        .lastReadAt(readStatus.getLastReadAt())
        .build());

  }

  /**
   * 읽음 상태 수정
   *
   * @param readStatusId            읽음 상태 ID
   * @param readStatusUpdateRequest 읽음 상태 수정 정보
   * @return 수정된 읽음 상태 정보
   */
  @Operation(
      summary = "읽음 상태 수정",
      description = "지정된 읽음 상태의 마지막 읽은 시간을 업데이트합니다.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "읽음 상태 수정 성공",
              content = @Content(schema = @Schema(implementation = ReadStatusApiDTO.FindReadStatusResponse.class))
          ),
          @ApiResponse(
              responseCode = "404",
              description = "읽음 상태를 찾을 수 없음",
              content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
          )
      }
  )
  @PatchMapping(value = "/{readStatusId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReadStatusApiDTO.FindReadStatusResponse> updateReadStatus(
      @Parameter(description = "읽음 상태 ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
      @PathVariable UUID readStatusId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "읽음 상태 수정 정보",
          required = true,
          content = @Content(schema = @Schema(implementation = ReadStatusUpdateRequest.class))
      )
      @RequestBody @Valid ReadStatusUpdateRequest readStatusUpdateRequest) {


    ReadStatusDTO.ReadStatus readStatus = readStatusService.updateReadStatus(ReadStatusDTO.UpdateReadStatusCommand.builder()
        .id(readStatusId)
        .lastReadAt(readStatusUpdateRequest.newLastReadAt())
        .build());

    return ResponseEntity.ok(ReadStatusApiDTO.FindReadStatusResponse.builder()
        .id(readStatus.getId())
        .channelId(readStatus.getChannelId())
        .userId(readStatus.getUserId())
        .lastReadAt(readStatus.getLastReadAt())
        .build());

  }

  /**
   * 사용자별 읽음 상태 목록 조회
   *
   * @param userId 사용자 ID
   * @return 읽음 상태 목록
   */
  @Operation(
      summary = "사용자별 읽음 상태 목록 조회",
      description = "지정된 사용자의 모든 채널에 대한 읽음 상태 목록을 조회합니다.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "읽음 상태 목록 조회 성공",
              content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReadStatusApiDTO.FindReadStatusResponse.class)))
          )
      }
  )
  @GetMapping()
  public ResponseEntity<List<ReadStatusApiDTO.FindReadStatusResponse>> findAllReadStatusByUserId(
      @Parameter(description = "사용자 ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
      @RequestParam UUID userId) {

    List<ReadStatusApiDTO.FindReadStatusResponse> readStatusList = readStatusService.findAllReadStatusByUserId(
            userId).stream()
        .map(readStatus -> ReadStatusApiDTO.FindReadStatusResponse.builder()
            .id(readStatus.getId())
            .channelId(readStatus.getChannelId())
            .userId(readStatus.getUserId())
            .lastReadAt(readStatus.getLastReadAt())
            .build())
        .toList();

    return ResponseEntity.ok(readStatusList);

  }

  /**
   * 데이터 없음 예외 처리
   *
   * @param e 발생한 예외
   * @return 에러 응답
   */
  @io.swagger.v3.oas.annotations.Hidden
  @ExceptionHandler(NoSuchDataBaseRecordException.class)
  public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleNoSuchDataBaseRecordException(
      NoSuchDataBaseRecordException e) {

    log.error("NoSuchDataBaseRecordException occurred", e);

    return ResponseEntity.status(404).body(ErrorApiDTO.ErrorApiResponse.builder()
        .code(HttpStatus.NOT_FOUND.value())
        .message(e.getMessage())
        .build());

  }

  /**
   * 이미 존재하는 데이터 예외 처리
   *
   * @param e 발생한 예외
   * @return 에러 응답
   */
  @io.swagger.v3.oas.annotations.Hidden
  @ExceptionHandler(AllReadyExistDataBaseRecordException.class)
  public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleAllReadyExistDataException(
      AllReadyExistDataBaseRecordException e) {

    log.error("AllReadyExistDataException occurred", e);

    return ResponseEntity.status(400).body(ErrorApiDTO.ErrorApiResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(e.getMessage())
        .build());

  }

}
