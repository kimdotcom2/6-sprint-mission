package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BinaryContentDTO {

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
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
