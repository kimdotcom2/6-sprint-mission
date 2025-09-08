package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.FileType;

import java.util.UUID;

public class BinaryContentDTO {

    public record CreateBinaryContentRequest(byte[] content, FileType fileType) {

    }

    public record ReadBinaryContentResult(
            UUID id,
            long createdAt,
            byte[] content,
            FileType fileType) {

    }

}
