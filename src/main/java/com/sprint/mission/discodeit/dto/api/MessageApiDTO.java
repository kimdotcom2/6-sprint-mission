package com.sprint.mission.discodeit.dto.api;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

public class MessageApiDTO {

    @Builder
    public record CreateMessageRequest(String content, boolean isReply, UUID parentMessageId, UUID channelId, UUID userId, List<UUID> binaryContentList) {

    }

    @Builder
    public record UpdateMessageRequest(UUID id, String content, boolean isReply, UUID parentMessageId) {

    }

    @Builder
    public record DeleteMessageRequest(UUID id) {

    }

    @Builder
    public record FindMessageResponse(
            UUID id,
            String content,
            boolean isReply,
            UUID parentMessageId,
            UUID channelId,
            UUID userId,
            List<UUID> binaryContentList,
            Long createdAt,
            Long updatedAt
    ) {

    }

}
