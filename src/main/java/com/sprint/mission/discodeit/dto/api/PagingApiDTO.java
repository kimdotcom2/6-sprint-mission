package com.sprint.mission.discodeit.dto.api;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import lombok.Builder;

public class PagingApiDTO {

  @Builder
  public record OffsetRequest(
      @NotBlank(message = "올바르지 않은 페이지 형식입니다.") int size,
      @NotBlank(message = "올바르지 않은 페이지 형식입니다.") int page,
      String sort
  ) {}

  @Builder
  public record CursorRequest(
      @NotBlank(message = "올바르지 않은 페이지 형식입니다.") int size,
      String sort
  ) {}

  @Builder
  public record OffsetPageResponse<T>(
      List<T> content,
      int number,
      int size,
      boolean hasNext,
      Long totalElements
  ) {}

  @Builder
  public record CursorPageResponse<T>(
      List<T> content,
      T nextCursor,
      int size,
      boolean hasNext,
      Long totalElements
  ) {}

}
