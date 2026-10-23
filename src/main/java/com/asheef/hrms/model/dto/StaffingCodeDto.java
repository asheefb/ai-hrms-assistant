package com.asheef.hrms.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StaffingCodeDto {

    private Integer manpowerCount = 1;

    private Integer budgetedCtc;

    @NotNull(message = "Staffing code is required")
    private String staffingCode;

    @NotNull(message = "Unit head is required")
    private Integer unitHead;

    @NotNull(message = "HR manager is required")
    private Integer hrManager;

    @NotNull(message = "Director approver is required")
    private Integer dirApprover;

    private String notes;

    private Integer membershipRequired = 0;
}
