package com.asheef.hrms.common.api;

import org.springframework.data.domain.Page;

import java.util.List;

/** Our own page shape, so the API does not expose Spring Data's Page internals. */
public record PageResponse<T>(List<T> content, int page, int size, long totalElements, int totalPages) {
    public static <T> PageResponse<T> from(Page<T> p) {
        return new PageResponse<>(p.getContent(), p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }
}
