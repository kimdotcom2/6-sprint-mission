package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.MessageDTO;
import com.sprint.mission.discodeit.dto.api.MessageApiDTO;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @RequestMapping(value = "/api/message/send", method = RequestMethod.POST)
    public ResponseEntity<String> sendMessage(@RequestBody MessageApiDTO.CreateMessageRequest request) {

        MessageDTO.CreateMessageCommand createMessageCommand = MessageDTO.CreateMessageCommand.builder()
                .content(request.content())
                .isReply(request.isReply())
                .parentMessageId(request.parentMessageId())
                .channelId(request.channelId())
                .userId(request.userId())
                .binaryContentList(request.binaryContentList())
                .build();

        messageService.createMessage(createMessageCommand);

        return ResponseEntity.ok("Message sent successfully");

    }

    @RequestMapping(value = "/api/message/update", method = RequestMethod.PUT)
    public ResponseEntity<String> updateMessage(@RequestBody MessageApiDTO.UpdateMessageRequest request) {

        MessageDTO.UpdateMessageCommand updateMessageCommand = MessageDTO.UpdateMessageCommand.builder()
                .id(request.id())
                .content(request.content())
                .isReply(request.isReply())
                .parentMessageId(request.parentMessageId())
                .build();

        messageService.updateMessage(updateMessageCommand);

        return ResponseEntity.ok("Message updated successfully");

    }

    @RequestMapping(value = "/api/message/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMessage(@RequestBody MessageApiDTO.DeleteMessageRequest deleteMessageRequest) {

        messageService.deleteMessageById(deleteMessageRequest.id());

        return ResponseEntity.ok("Message deleted successfully");

    }

    @RequestMapping(value = "/api/channel/{channelId}/messages", method = RequestMethod.GET)
    public List<MessageApiDTO.FindMessageResponse> findMessagesByChannelId(@PathVariable UUID channelId) {

        return messageService.findMessagesByChannelId(channelId).stream()
                .map(message -> MessageApiDTO.FindMessageResponse.builder()
                        .id(message.id())
                        .content(message.content())
                        .isReply(message.isReply())
                        .parentMessageId(message.parentMessageId())
                        .channelId(message.channelId())
                        .userId(message.userId())
                        .binaryContentList(message.binaryContentList())
                        .createdAt(message.createdAt())
                        .updatedAt(message.updatedAt())
                        .build())
                .toList();

    }

}
