package com.asheef.hrms.model.dto;

import lombok.Data;

@Data
public class EmployeeUpdateDto {

    private String firstName;
    private String lastName;
    private String officeEmail;
    private String personalEmail;

}
