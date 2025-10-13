package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.enums.ContentType;
import java.time.Instant;
import java.time.LocalDateTime;
import lombok.Builder;

import java.util.UUID;

public class BinaryContentApiDTO {

    @Builder
    public record ReadBinaryContentResponse(
            UUID id,
            String fileName,
            Long size,
            @JsonProperty("contentType")
            ContentType contentType
    ) {

    }

}
