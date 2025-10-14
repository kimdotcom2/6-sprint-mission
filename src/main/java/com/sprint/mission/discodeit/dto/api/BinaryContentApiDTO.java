package com.sprint.mission.discodeit.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sprint.mission.discodeit.enums.ContentType;
import java.util.UUID;
import lombok.Builder;

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
