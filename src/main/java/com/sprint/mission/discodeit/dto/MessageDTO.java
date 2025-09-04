package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.UUID;

public class MessageDTO {

    //message update를 위한 Request DTO
    @Builder
    public record UpdateMessageRequest(UUID id, String content, boolean isReply, UUID parentMessageId) {

    }

}
