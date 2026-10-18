package com.asheef.hrms.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeUpdateDto {
    
    private String firstName;
    private String lastName;
    private String officeEmail;
    private String personalEmail;
    private Integer department;
    private Integer gender;
    private Integer status;
    private Integer designation;
    private Integer employeeType;
    private Integer reportingTo;
    private LocalDate dateOfJoin;
    private Integer location;
    private Integer subLocation;
    private LocalDate dateOfBirth;
    private String fatherName;
    private Integer nationality;
    private String countryCode;
    private String contactNumber;
    private Integer modifiedBy;
}
