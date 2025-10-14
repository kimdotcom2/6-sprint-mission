package com.sprint.mission.discodeit.dto.api;

import java.util.List;
import lombok.Builder;

public class PagingApiDTO {

  @Builder
  public record OffsetRequest(
      int size,
      int page,
      String sort
  ) {}

  @Builder
  public record OffsetResponse<T>(
      List<T> content,
      int number,
      int size,
      boolean hasNext,
      Long totalElements
  ) {}

}
