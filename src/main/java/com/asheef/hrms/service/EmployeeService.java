package com.asheef.hrms.service;

import com.asheef.hrms.constants.Constant;
import com.asheef.hrms.entity.Employee;
import com.asheef.hrms.entity.MobileCountryCodes;
import com.asheef.hrms.model.dto.EmployeeRequestDto;
import com.asheef.hrms.repo.EmployeeRepository;
import com.asheef.hrms.repo.MobileCountryCodesRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final MobileCountryCodesRepository mobileCountryCodesRepository;

    public EmployeeService(EmployeeRepository employeeRepository, MobileCountryCodesRepository mobileCountryCodesRepository) {
        this.employeeRepository = employeeRepository;
        this.mobileCountryCodesRepository = mobileCountryCodesRepository;
    }


    public String createEmployee(EmployeeRequestDto employeeDto) {
        Employee employee = new Employee();

        employee.setEmpNumber(employeeDto.getEmpNumber());
        employee.setEmpFirstname(employeeDto.getEmpFirstname());
        employee.setEmpLastname(employeeDto.getEmpLastname());
        employee.setOfficialEmail(employeeDto.getOfficialEmail());
        employee.setPersonalEmail(employeeDto.getPersonalEmail());

        employee.setDepartment(employeeDto.getDepartment());
        employee.setStatus(employeeDto.getStatus());
        employee.setGender(employeeDto.getGender());
        employee.setDesignation(employeeDto.getDesignation());
        employee.setEmployeeType(employeeDto.getEmployeeType());
        employee.setReportingTo(employeeDto.getReportingTo());

        employee.setDateOfJoin(employeeDto.getDateOfJoin());
        employee.setLocation(employeeDto.getLocation());
        employee.setSubLocation(employeeDto.getSubLocation());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setFatherName(employeeDto.getFatherName());

        employee.setNationality(employeeDto.getNationality());

        
        if (employeeDto.getCountryCode() != null && !employeeDto.getCountryCode().isEmpty() && 
            (employeeDto.getContactNumber() == null || employeeDto.getContactNumber().isEmpty())) 
            throw new IllegalArgumentException("Contact number is required when country code is provided.");
            
        else if ((employeeDto.getCountryCode() == null || employeeDto.getCountryCode().isEmpty()) && 
                 (employeeDto.getContactNumber() != null && !employeeDto.getContactNumber().isEmpty())) 
            throw new IllegalArgumentException("Country code is required when contact number is provided.");
        else if (employeeDto.getCountryCode() != null && !employeeDto.getCountryCode().isEmpty()) {
            MobileCountryCodes mobileCountryCodes = mobileCountryCodesRepository.findByCountryCode(employeeDto.getCountryCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid country code: " + employeeDto.getCountryCode()));

            if (employeeDto.getContactNumber().length() != mobileCountryCodes.getNumberLength()) {
                throw new IllegalArgumentException("Invalid contact number length for country code: " + employeeDto.getCountryCode());
            }

            employee.setCountryCode(employeeDto.getCountryCode());
        }

        

        

        employee.setContactNumber(employeeDto.getContactNumber());

        employee.setUserId(employeeDto.getUserId());
        employee.setCreatedBy(employeeDto.getCreatedBy());
        employee.setModifiedBy(employeeDto.getModifiedBy());

        employeeRepository.save(employee);
        return Constant.SUCCESS;
    }
}
