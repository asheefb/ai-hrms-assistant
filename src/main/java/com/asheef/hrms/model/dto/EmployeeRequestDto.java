
package com.asheef.hrms.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class EmployeeRequestDto {

    @NotBlank(message = "Employee number is required")
    @Size(max = 20, message = "Employee number must not exceed 20 characters")
    private String empNumber;

    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String empFirstname;

    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String empLastname;

    private String officialEmail;

    private String personalEmail;

    @NotNull(message = "Department is required")
    private Integer department;

    @NotNull(message = "Employee status is required")
    private Integer status;

    @NotNull(message = "Gender is required")
    private Integer gender;

    @NotNull(message = "Designation is required")
    private Integer designation;

    @NotNull(message = "Employee type is required")
    private Integer employeeType;

    private Long reportingTo;

    @NotNull(message = "Date of joining is required")
    private LocalDate dateOfJoin;

    private Integer location;

    private Integer subLocation;

    private LocalDate dateOfBirth;

    @Size(max = 60, message = "Father's name must not exceed 60 characters")
    private String fatherName;

    @NotNull(message = "Nationality is required")
    private Integer nationality;

    @Size(max = 5, message = "Country code must not exceed 5 characters")
    private String countryCode;

    private String contactNumber;

    private Integer userId;

    @NotNull(message = "Created by is required")
    private Integer createdBy;

    @NotNull(message = "Modified by is required")
    private Integer modifiedBy;
}
