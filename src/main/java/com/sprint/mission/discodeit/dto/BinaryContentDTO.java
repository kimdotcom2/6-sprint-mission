package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.FileType;
import lombok.Builder;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class BinaryContentDTO {

  @Getter
  @Builder
  @RequiredArgsConstructor
  public static class BinaryContent {

    private UUID id;
    private Long createdAt;
    private String fileName;
    private Long size;
    private FileType fileType;
    private byte[] bytes;

  }

  @Builder
  public record CreateBinaryContentCommand(String fileName, byte[] data, FileType fileType) {

  }

  @Builder
  public record ReadBinaryContentResult(
      UUID id,
      Long createdAt,
      String fileName,
      Long size,
      byte[] data,
      FileType fileType) {

  }

}
