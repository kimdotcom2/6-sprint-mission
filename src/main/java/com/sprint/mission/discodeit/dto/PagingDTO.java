package com.sprint.mission.discodeit.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PagingDTO {

  @Getter
  @Builder
  @RequiredArgsConstructor
  public static class Paging<T> {

    private List<T> content;
    private int number;
    private int size;
    private boolean hasNext;
    private Long totalElement;

  }

}
