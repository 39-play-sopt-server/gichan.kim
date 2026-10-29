package org.sopt.core.support.response;

import org.sopt.core.support.PageResult;

import java.util.List;

public record PageResponse<T>(List<T> content, int page, int size, long totalElements, int totalPages, boolean hasNext) {

    public static <T> PageResponse<T> from(PageResult<T> result) {
        return new PageResponse<>(
                result.content(),
                result.page(),
                result.size(),
                result.totalElements(),
                result.totalPages(),
                result.hasNext()
        );
    }
}
