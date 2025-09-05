package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

public class MessageDTO {

    @Builder
    public record CreateMessageRequest(String content, boolean isReply, UUID parentMessageId, UUID channelId, UUID userId, List<UUID> binaryContentList) {

        public CreateMessageRequest {

            if (isReply && parentMessageId == null) {
                throw new IllegalArgumentException("Parent message id is required.");
            }

            if (!isReply && parentMessageId != null) {
                throw new IllegalArgumentException("Parent message id is not required.");
            }

        }

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

        public UpdateMessageRequest {

            if (isReply && parentMessageId == null) {
                throw new IllegalArgumentException("Parent message id is required.");
            }

            if (!isReply && parentMessageId != null) {
                throw new IllegalArgumentException("Parent message id is not required.");
            }

        }

    }

}
