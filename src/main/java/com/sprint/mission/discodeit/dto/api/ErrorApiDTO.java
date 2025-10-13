package com.sprint.mission.discodeit.dto.api;

import lombok.Builder;

public class ErrorApiDTO {

  @Builder
  public record ErrorApiResponse(
      Integer code,
      String message
  ) {

  }

}
