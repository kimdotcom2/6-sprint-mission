package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.FileType;
import lombok.Builder;

import java.util.UUID;

public class BinaryContentDTO {

    @Builder
    public record CreateBinaryContentCommand(byte[] data, FileType fileType) {

    }

    @Builder
    public record ReadBinaryContentResult(
            UUID id,
            Long createdAt,
            byte[] data,
            FileType fileType) {

    }

}
