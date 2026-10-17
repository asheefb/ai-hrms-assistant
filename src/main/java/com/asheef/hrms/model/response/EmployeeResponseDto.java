package com.asheef.hrms.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeResponseDto {

    private Integer id;
    private String empNumber;
    private String firstName;
    private String lastName;
    private String officeEmail;
    private String personalEmail;
    private String department;
    private String gender;
    private String status;
    private String designation;
    private String employeeType;
    private String reportingTo;
    private String dateOfJoin;
    private String location;
    private String subLocation;
    private String dateOfBirth;
    private String fatherName;
    private String nationality;
    private String countryCode;
    private String contactNumber;
    private String userId;
    private String createdBy;
    private LocalDateTime created;
    private String modifiedBy;
    private LocalDateTime modified;
}
