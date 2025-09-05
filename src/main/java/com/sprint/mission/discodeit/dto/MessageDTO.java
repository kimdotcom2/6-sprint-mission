package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

public class MessageDTO {

    @Builder
    public record CreateMessageRequest(String content, boolean isReply, UUID parentMessageId, UUID channelId, UUID userId, List<UUID> binaryContentList) {

    }

    @Builder
    public record FindMessageResult(
            UUID id,
            String content,
            boolean isReply,
            UUID parentMessageId,
            UUID channelId,
            UUID userId,
            List<UUID>
            binaryContentList,
            Long createdAt,
            Long updatedAt) {

    }

    //message update를 위한 Request DTO
    @Builder
    public record UpdateMessageRequest(UUID id, String content, boolean isReply, UUID parentMessageId) {

    }

}
