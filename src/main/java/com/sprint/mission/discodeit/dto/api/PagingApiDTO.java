package com.sprint.mission.discodeit.dto.api;

import java.util.List;

public class PagingApiDTO {

  public record Response<T>(
      List<T> content,
      int number,
      int size,
      boolean hasNext,
      Long totalElements
  ) {}

}
