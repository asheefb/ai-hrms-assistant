package com.asheef.hrms.model.dto;

import lombok.Data;

@Data
public class StaffingCodeUpdateDto {

    private Integer manpowerCount;

    private Integer budgetedCtc;

    private String staffingCode;

    private Integer unitHead;

    private Integer hrManager;

    private Integer dirApprover;

    private String notes;

    private Integer membershipRequired;
}
