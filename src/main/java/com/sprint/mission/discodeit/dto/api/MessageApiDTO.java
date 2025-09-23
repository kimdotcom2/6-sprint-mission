package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class MessageApiDTO {

    @Builder
    public record MessageCreateRequest(
            String content,
            boolean isReply,
            UUID parentMessageId,
            UUID channelId,
            @JsonProperty("authorId")
            UUID userId) {

    }

    @Builder
    public record MessageUpdateRequest(
        @JsonProperty("newContent")
        String content,
        boolean isReply,
        UUID parentMessageId) {

    }

    @Builder
    public record FindMessageResponse(
            UUID id,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String content,
            UUID channelId,
            @JsonProperty("authorId")
            UUID userId,
            boolean isReply,
            UUID parentMessageId,
            @JsonProperty("attachmentIds")
            List<UUID> binaryContentList
    ) {

    }

}
