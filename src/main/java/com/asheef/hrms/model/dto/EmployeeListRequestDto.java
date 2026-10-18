package com.asheef.hrms.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeListRequestDto extends PageRequestDto {
    private List<Integer> status;
 
    private String search;

    private String fieldName;
}
