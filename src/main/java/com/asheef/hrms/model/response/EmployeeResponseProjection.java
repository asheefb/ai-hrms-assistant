package com.asheef.hrms.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EmployeeResponseProjection {

    Integer getId();

    String getEmpNumber();

    String getFirstName();

    String getLastName();

    String getOfficeEmail();

    String getPersonalEmail();

    String getDepartment();

    String getGender();

    String getStatus();

    String getDesignation();

    String getEmployeeType();

    String getReportingTo();

    LocalDate getDateOfJoin();

    String getLocation();

    String getSubLocation();

    LocalDate getDateOfBirth();

    String getFatherName();

    String getNationality();

    String getCountryCode();

    String getContactNumber();

    String getUserId();

    String getCreatedBy();

    LocalDateTime getCreated();

    String getUpdatedBy();

    LocalDateTime getModified();
}