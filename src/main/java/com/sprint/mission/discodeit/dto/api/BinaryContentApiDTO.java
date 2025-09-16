package com.sprint.mission.discodeit.dto.api;

import com.sprint.mission.discodeit.enums.FileType;
import lombok.Builder;

import java.util.UUID;

public class BinaryContentApiDTO {

    @Builder
    public record ReadBinaryContentResponse(
            UUID id,
            Long createdAt,
            byte[] data,
            FileType fileType
    ) {

    }

}
