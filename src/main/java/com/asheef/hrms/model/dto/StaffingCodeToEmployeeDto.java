package com.asheef.hrms.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffingCodeToEmployeeDto {

    @NotNull(message = "Staffing code ID is required")
    @Positive(message = "Staffing code ID must be positive")
    private Integer staffingCodeId;

    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be positive")
    private Integer employeeId;
}
