package com.sprint.mission.discodeit.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PagingDTO {

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class OffsetRequest {
        private final int page;
        private final int size;
        private final String sort;

        public static OffsetRequest of(int page, int size) {
            return new OffsetRequest(page, size, null);
        }

        public static OffsetRequest of(int page, int size, String sort) {
            return new OffsetRequest(page, size, sort);
        }
    }

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class OffsetPage<T> {
        private final List<T> content;
        private final int number;
        private final int size;
        private final boolean hasNext;
        private final Long totalElement;
    }
}
