package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.ContentType;
import java.time.Instant;
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
    private Instant createdAt;
    private String fileName;
    private Long size;
    private ContentType contentType;
    private byte[] bytes;

  }

  @Builder
  public record BinaryContentCreateCommand(String fileName, byte[] data, ContentType contentType) {

  }

}
