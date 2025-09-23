package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.BinaryContentDTO;
import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.dto.api.ErrorApiDTO;
import com.sprint.mission.discodeit.dto.api.MessageApiDTO;
import com.sprint.mission.discodeit.dto.api.MessageApiDTO.MessageUpdateRequest;
import com.sprint.mission.discodeit.enums.FileType;
import com.sprint.mission.discodeit.exception.NoSuchDataException;
import com.sprint.mission.discodeit.service.MessageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageApiDTO.FindMessageResponse> sendMessage(@RequestPart MessageApiDTO.MessageCreateRequest request, @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments) {

        List<BinaryContentDTO.CreateBinaryContentCommand> binaryContentList = attachments.stream().map(file -> {
          try {
            return BinaryContentDTO.CreateBinaryContentCommand.builder()
                    .fileName(file.getOriginalFilename())
                    .fileType(FileType.ETC)
                    .data(file.getBytes())
                    .build();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }).toList();

        MessageDTO.CreateMessageCommand createMessageCommand = MessageDTO.CreateMessageCommand.builder()
                .content(request.content())
                .isReply(request.isReply())
                .parentMessageId(request.parentMessageId())
                .channelId(request.channelId())
                .userId(request.userId())
                .binaryContentList(binaryContentList)
                .build();

        messageService.createMessage(createMessageCommand);

        MessageDTO.FindMessageResult message = messageService.findMessagesByUserId(request.userId()).stream()
                .filter(message1 -> message1.content().equals(request.channelId()))
                .sorted((message1, message2) -> message2.createdAt().compareTo(message1.createdAt()))
                .limit(1)
                .findFirst()
                .orElseThrow(() -> new NoSuchDataException("No such message."));

        return ResponseEntity.status(201).body(MessageApiDTO.FindMessageResponse.builder()
                .id(message.id())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(message.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(message.updatedAt()), ZoneId.systemDefault()))
                .content(message.content())
                .isReply(message.isReply())
                .parentMessageId(message.parentMessageId())
                .channelId(message.channelId())
                .userId(message.userId())
                .binaryContentList(message.binaryContentList())
                .build());

    }

    @PatchMapping(value = "/{messageId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageApiDTO.FindMessageResponse> updateMessage(@PathVariable UUID messageId, @RequestBody MessageUpdateRequest request) {

        MessageDTO.UpdateMessageCommand updateMessageCommand = MessageDTO.UpdateMessageCommand.builder()
                .id(messageId)
                .content(request.content())
                .isReply(request.isReply())
                .parentMessageId(request.parentMessageId())
                .build();

        messageService.updateMessage(updateMessageCommand);

        MessageDTO.FindMessageResult message = messageService.findMessageById(messageId)
            .orElseThrow(() -> new NoSuchDataException("No such message."));

        return ResponseEntity.ok().body(MessageApiDTO.FindMessageResponse.builder()
                .id(message.id())
                .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(message.createdAt()), ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(message.updatedAt()), ZoneId.systemDefault()))
                .content(message.content())
                .isReply(message.isReply())
                .parentMessageId(message.parentMessageId())
                .channelId(message.channelId())
                .userId(message.userId())
                .binaryContentList(message.binaryContentList())
                .build());

    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable UUID messageId) {

        messageService.deleteMessageById(messageId);

        MessageDTO.FindMessageResult message = messageService.findMessageById(messageId)
                .orElseThrow(() -> new NoSuchDataException("No such message."));

        return ResponseEntity.status(204).build();

    }

    @GetMapping()
    public ResponseEntity<List<MessageApiDTO.FindMessageResponse>> findAllByChannelId(@RequestParam UUID channelId) {

        List<MessageDTO.FindMessageResult> messages = messageService.findMessagesByChannelId(channelId);

        return ResponseEntity.ok(messages.stream()
                .map(message -> MessageApiDTO.FindMessageResponse.builder()
                        .id(message.id())
                        .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(message.createdAt()), ZoneId.systemDefault()))
                        .updatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(message.updatedAt()), ZoneId.systemDefault()))
                        .content(message.content())
                        .isReply(message.isReply())
                        .parentMessageId(message.parentMessageId())
                        .channelId(message.channelId())
                        .userId(message.userId())
                        .binaryContentList(message.binaryContentList())
                        .build())
                .toList());

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
