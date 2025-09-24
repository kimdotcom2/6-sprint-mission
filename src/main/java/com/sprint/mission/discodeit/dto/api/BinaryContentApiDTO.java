package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.enums.FileType;
import java.time.LocalDateTime;
import lombok.Builder;

import java.util.UUID;

public class BinaryContentApiDTO {

    @Builder
    public record ReadBinaryContentResponse(
            UUID id,
            LocalDateTime createdAt,
            String fileName,
            Long size,
            @JsonProperty("contentType")
            FileType fileType,
            @JsonProperty("bytes")
            byte[] data
    ) {

    }

}
