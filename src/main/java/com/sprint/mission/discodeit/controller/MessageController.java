package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentDTO.BinaryContentCreateCommand;
import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.dto.api.BinaryContentApiDTO;
import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.dto.api.MessageApiDTO;
import com.sprint.mission.discodeit.dto.api.MessageApiDTO.MessageUpdateRequest;
import com.sprint.mission.discodeit.dto.api.UserApiDTO;
import com.sprint.mission.discodeit.enums.ContentType;
import com.sprint.mission.discodeit.exception.NoSuchDataBaseRecordException;
import com.sprint.mission.discodeit.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * 메시지 관련 API 컨트롤러
 */
@Tag(name = "메시지 API", description = "메시지 전송 및 관리를 위한 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

  private final MessageService messageService;

  /**
   * 메시지 전송
   *
   * @param messageCreateRequest 메시지 생성 요청 정보
   * @param attachments          첨부 파일 목록
   * @return 생성된 메시지 정보
   */
  @Operation(
      summary = "메시지 전송",
      description = "새로운 메시지를 전송합니다. 파일을 첨부할 수 있습니다.",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "메시지 전송 성공",
              content = @Content(schema = @Schema(implementation = MessageApiDTO.FindMessageResponse.class))
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
          )
      }
  )
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<MessageApiDTO.FindMessageResponse> sendMessage(
      @Parameter(description = "메시지 생성 요청 정보", required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = MessageApiDTO.MessageCreateRequest.class)))
      @RequestPart @Valid MessageApiDTO.MessageCreateRequest messageCreateRequest,
      @Parameter(description = "첨부 파일 목록")
      @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments) {

    List<BinaryContentCreateCommand> binaryContentList = attachments != null ?
        attachments.stream().map(file -> {
          try {
            return BinaryContentCreateCommand.builder()
                .fileName(file.getOriginalFilename())
                .contentType(ContentType.ETC)
                .data(file.getBytes())
                .build();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }).toList() : List.of();

    MessageDTO.CreateMessageCommand createMessageCommand = MessageDTO.CreateMessageCommand.builder()
        .content(messageCreateRequest.content())
        .channelId(messageCreateRequest.channelId())
        .userId(messageCreateRequest.authorId())
        .binaryContentList(binaryContentList)
        .build();

    MessageDTO.Message message = messageService.createMessage(createMessageCommand);

    return ResponseEntity.status(HttpStatus.CREATED).body(
        MessageApiDTO.FindMessageResponse.builder()
            .id(message.getId())
            .createdAt(message.getCreatedAt())
            .updatedAt(message.getUpdatedAt())
            .content(message.getContent())
            .channelId(message.getChannelId())
            .author(UserApiDTO.FindUserResponse.builder()
                .id(message.getAuthor().getId())
                .username(message.getAuthor().getUsername())
                .email(message.getAuthor().getEmail())
                .profile(BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                    .id(message.getAuthor().getProfileId().getId())
                    .fileName(message.getAuthor().getProfileId().getFileName())
                    .size(message.getAuthor().getProfileId().getSize())
                    .contentType(message.getAuthor().getProfileId().getContentType())
                    .build())
                .isOnline(message.getAuthor().getIsOnline())
                .build())
            .attachments(message.getAttachments().stream().map(attachment ->
                BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                    .id(attachment.getId())
                    .fileName(attachment.getFileName())
                    .size(attachment.getSize())
                    .contentType(attachment.getContentType())
                    .build()
            ).toList())
            .build());

  }

  /**
   * 메시지 수정
   *
   * @param messageId            메시지 ID
   * @param messageUpdateRequest 메시지 수정 정보
   * @return 수정된 메시지 정보
   */
  @Operation(
      summary = "메시지 수정",
      description = "기존 메시지의 내용을 수정합니다.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "메시지 수정 성공",
              content = @Content(schema = @Schema(implementation = MessageApiDTO.FindMessageResponse.class))
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
          ),
          @ApiResponse(
              responseCode = "404",
              description = "메시지를 찾을 수 없음",
              content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
          )
      }
  )

  @PatchMapping(value = "/{messageId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MessageApiDTO.FindMessageResponse> updateMessage(
      @Parameter(description = "메시지 ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
      @PathVariable UUID messageId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "메시지 수정 정보",
          required = true,
          content = @Content(schema = @Schema(implementation = MessageUpdateRequest.class))
      )
      @RequestBody @Valid MessageUpdateRequest messageUpdateRequest) {

    MessageDTO.UpdateMessageCommand updateMessageCommand = MessageDTO.UpdateMessageCommand.builder()
        .id(messageId)
        .content(messageUpdateRequest.content())
        .build();

    MessageDTO.Message message = messageService.updateMessage(updateMessageCommand);

    return ResponseEntity.ok(MessageApiDTO.FindMessageResponse.builder()
        .id(message.getId())
        .createdAt(message.getCreatedAt())
        .updatedAt(message.getUpdatedAt())
        .content(message.getContent())
        .channelId(message.getChannelId())
        .author(UserApiDTO.FindUserResponse.builder()
            .id(message.getAuthor().getId())
            .username(message.getAuthor().getUsername())
            .email(message.getAuthor().getEmail())
            .profile(BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                .id(message.getAuthor().getProfileId().getId())
                .fileName(message.getAuthor().getProfileId().getFileName())
                .size(message.getAuthor().getProfileId().getSize())
                .contentType(message.getAuthor().getProfileId().getContentType())
                .build())
            .isOnline(message.getAuthor().getIsOnline())
            .build())
        .attachments(message.getAttachments().stream().map(attachment ->
            BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .size(attachment.getSize())
                .contentType(attachment.getContentType())
                .build()
        ).toList())
        .build());

  }

  /**
   * 메시지 삭제
   *
   * @param messageId 메시지 ID
   * @return 삭제 결과
   */
  @Operation(
      summary = "메시지 삭제",
      description = "메시지를 삭제합니다.",
      responses = {
          @ApiResponse(
              responseCode = "204",
              description = "메시지 삭제 성공"
          ),
          @ApiResponse(
              responseCode = "404",
              description = "메시지를 찾을 수 없음",
              content = @Content(schema = @Schema(implementation = ErrorApiDTO.ErrorApiResponse.class))
          )
      }
  )
  @DeleteMapping("/{messageId}")
  public ResponseEntity<Void> deleteMessage(
      @Parameter(description = "메시지 ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
      @PathVariable UUID messageId) {

    messageService.deleteMessageById(messageId);

    return ResponseEntity.status(204).build();

  }

  /**
   * 채널별 메시지 목록 조회
   *
   * @param channelId 채널 ID
   * @return 메시지 목록
   */
  @Operation(
      summary = "채널별 메시지 목록 조회",
      description = "특정 채널의 모든 메시지를 조회합니다.",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "메시지 목록 조회 성공",
              content = @Content(array = @ArraySchema(schema = @Schema(implementation = MessageApiDTO.FindMessageResponse.class)))
          )
      }
  )
  @GetMapping()
  public ResponseEntity<List<MessageApiDTO.FindMessageResponse>> findAllByChannelId(
      @Parameter(description = "채널 ID", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
      @RequestParam UUID channelId) {

    List<MessageDTO.Message> messageList = messageService.findMessagesByChannelId(channelId);

    return ResponseEntity.ok(messageList.stream()
        .map(message -> MessageApiDTO.FindMessageResponse.builder()
            .id(message.getId())
            .createdAt(message.getCreatedAt())
            .updatedAt(message.getUpdatedAt())
            .content(message.getContent())
            .channelId(message.getChannelId())
            .author(UserApiDTO.FindUserResponse.builder()
                .id(message.getAuthor().getId())
                .username(message.getAuthor().getUsername())
                .email(message.getAuthor().getEmail())
                .profile(BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                    .id(message.getAuthor().getProfileId().getId())
                    .fileName(message.getAuthor().getProfileId().getFileName())
                    .size(message.getAuthor().getProfileId().getSize())
                    .contentType(message.getAuthor().getProfileId().getContentType())
                    .build())
                .isOnline(message.getAuthor().getIsOnline())
                .build())
            .attachments(message.getAttachments().stream().map(attachment ->
                BinaryContentApiDTO.ReadBinaryContentResponse.builder()
                    .id(attachment.getId())
                    .fileName(attachment.getFileName())
                    .size(attachment.getSize())
                    .contentType(attachment.getContentType())
                    .build()
            ).toList())
            .build())
        .toList());

  }

  @ExceptionHandler(NoSuchDataBaseRecordException.class)
  public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleNoSuchDataException(
      NoSuchDataBaseRecordException e) {

    log.error("NoSuchDataException occurred", e);

    return ResponseEntity.status(404).body(ErrorApiDTO.ErrorApiResponse.builder()
        .code(HttpStatus.NOT_FOUND.value())
        .message(e.getMessage())
        .build());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorApiDTO.ErrorApiResponse> handleIllegalArgumentException(
      IllegalArgumentException e) {

    log.error("IllegalArgumentException occurred", e);

    return ResponseEntity.status(400).body(ErrorApiDTO.ErrorApiResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(e.getMessage())
        .build());
  }

}
