package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.api.PagingApiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public class PageResponseMapper<T> {

  public PagingApiDTO.PageResponse<T> fromSlice(Slice<T> slice) {
    return PagingApiDTO.PageResponse.<T>builder()
        .content(slice.getContent())
        .number(slice.getNumber())
        .size(slice.getSize())
        .hasNext(slice.hasNext())
        .totalElements(null)
        .build();
  }

  public PagingApiDTO.PageResponse<T> fromPage(Page<T> page) {
    return PagingApiDTO.PageResponse.<T>builder()
        .content(page.getContent())
        .number(page.getNumber())
        .size(page.getSize())
        .hasNext(page.hasNext())
        .totalElements(page.getTotalElements())
        .build();
  }

}
