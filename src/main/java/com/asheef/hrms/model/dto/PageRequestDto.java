package com.asheef.hrms.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PageRequestDto {

    @Min(value = 0, message = "Page number must be greater than or equal to 0")
    private Integer pageNumber = 0;

    @Min(value = 1, message = "Page size must be greater than or equal to 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    private Integer pageSize = 10;

    private String[] sortBy = {"id"};

    @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Sort direction must be either 'asc' or 'desc'")
    private String sortDirection = "asc";
}
