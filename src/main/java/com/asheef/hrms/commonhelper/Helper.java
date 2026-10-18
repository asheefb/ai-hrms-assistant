package com.asheef.hrms.commonhelper;

import com.asheef.hrms.entity.Employee;
import com.asheef.hrms.model.response.EmployeeResponseDto;
import com.asheef.hrms.model.response.EmployeeResponseProjection;

public class Helper {

    public static EmployeeResponseDto getEmployeeResponseDto(EmployeeResponseProjection employeeResponseProjection) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setId(employeeResponseProjection.getId());
        employeeResponseDto.setEmpNumber(employeeResponseProjection.getEmpNumber());
        employeeResponseDto.setFirstName(employeeResponseProjection.getFirstName());
        employeeResponseDto.setLastName(employeeResponseProjection.getLastName());
        employeeResponseDto.setOfficeEmail(employeeResponseProjection.getOfficeEmail());
        employeeResponseDto.setPersonalEmail(employeeResponseProjection.getPersonalEmail());
        employeeResponseDto.setDepartment(employeeResponseProjection.getDepartment());
        employeeResponseDto.setGender(employeeResponseProjection.getGender());
        employeeResponseDto.setStatus(employeeResponseProjection.getStatus());
        employeeResponseDto.setDesignation(employeeResponseProjection.getDesignation());
        employeeResponseDto.setEmployeeType(employeeResponseProjection.getEmployeeType());
        employeeResponseDto.setReportingTo(employeeResponseProjection.getReportingTo());
        employeeResponseDto.setDateOfJoin(employeeResponseProjection.getDateOfJoin() == null ? null : employeeResponseProjection.getDateOfJoin().toString());
        employeeResponseDto.setLocation(employeeResponseProjection.getLocation());
        employeeResponseDto.setSubLocation(employeeResponseProjection.getSubLocation());
        employeeResponseDto.setDateOfBirth(employeeResponseProjection.getDateOfBirth() == null ? null : employeeResponseProjection.getDateOfBirth().toString());
        employeeResponseDto.setFatherName(employeeResponseProjection.getFatherName());
        employeeResponseDto.setNationality(employeeResponseProjection.getNationality());
        employeeResponseDto.setCountryCode(employeeResponseProjection.getCountryCode());
        employeeResponseDto.setContactNumber(employeeResponseProjection.getContactNumber());
        employeeResponseDto.setUserId(employeeResponseProjection.getUserId());
        employeeResponseDto.setCreatedBy(employeeResponseProjection.getCreatedBy());
        employeeResponseDto.setCreated(employeeResponseProjection.getCreated());
        employeeResponseDto.setModifiedBy(employeeResponseProjection.getUpdatedBy());
        employeeResponseDto.setModified(employeeResponseProjection.getModified());
        return employeeResponseDto;
    }

    public static EmployeeResponseDto getEmployeeResponseDto(Employee employee) {

        EmployeeResponseDto dto = new EmployeeResponseDto();

        dto.setId(employee.getId());
        dto.setEmpNumber(employee.getEmpNumber());
        dto.setFirstName(employee.getEmpFirstname());
        dto.setLastName(employee.getEmpLastname());
        dto.setOfficeEmail(employee.getOfficialEmail());
        dto.setPersonalEmail(employee.getPersonalEmail());

        dto.setDateOfJoin(
                employee.getDateOfJoin() == null
                        ? null
                        : employee.getDateOfJoin().toString()
        );

        dto.setLocation(String.valueOf(employee.getLocation()));
        dto.setSubLocation(String.valueOf(employee.getSubLocation()));

        dto.setDateOfBirth(
                employee.getDateOfBirth() == null
                        ? null
                        : employee.getDateOfBirth().toString()
        );

        dto.setFatherName(employee.getFatherName());
        dto.setCountryCode(employee.getCountryCode());
        dto.setContactNumber(employee.getContactNumber());
        dto.setUserId(String.valueOf(employee.getUserId()));
        dto.setCreated(employee.getCreated());
        dto.setModified(employee.getModified());

        return dto;
    }
}
