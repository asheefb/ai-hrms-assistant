package com.asheef.hrms.service;

import com.asheef.hrms.constants.Constant;
import com.asheef.hrms.entity.Employee;
import com.asheef.hrms.entity.HrmModules;
import com.asheef.hrms.entity.MobileCountryCodes;
import com.asheef.hrms.entity.User;
import com.asheef.hrms.model.dto.EmployeeRequestDto;
import com.asheef.hrms.model.dto.EmployeeUpdateDto;
import com.asheef.hrms.model.response.EmployeeResponseDto;
import com.asheef.hrms.repo.EmployeeRepository;
import com.asheef.hrms.repo.HrmModulesRepository;
import com.asheef.hrms.repo.MobileCountryCodesRepository;
import com.asheef.hrms.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final MobileCountryCodesRepository mobileCountryCodesRepository;

    private final HrmModulesRepository hrmModulesRepository;

    private final UserRepository userRepository;

    public EmployeeService(EmployeeRepository employeeRepository, MobileCountryCodesRepository mobileCountryCodesRepository, HrmModulesRepository hrmModulesRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.mobileCountryCodesRepository = mobileCountryCodesRepository;
        this.hrmModulesRepository = hrmModulesRepository;
        this.userRepository = userRepository;
    }


    public String onboardEmployee(EmployeeRequestDto employeeDto) {
        Employee employee = new Employee();

        employee.setEmpNumber(employeeDto.getEmpNumber());
        employee.setEmpFirstname(employeeDto.getFirstName());
        employee.setEmpLastname(employeeDto.getLastName());
        employee.setOfficialEmail(employeeDto.getOfficialEmail());
        employee.setPersonalEmail(employeeDto.getPersonalEmail());

        Integer deptId = hrmModulesRepository.findById(employeeDto.getDepartment())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID: " + employeeDto.getDepartment())).getId();

        employee.setDepartment(deptId);

        Integer activeStatus = hrmModulesRepository.findChildByModuleCodes("CT02", "ST01")
                .orElseThrow(() -> new IllegalArgumentException("Actively Employed status not Configured")).getId();

        employee.setStatus(activeStatus);

        Integer genderId = hrmModulesRepository.findById(employeeDto.getGender())
                .orElseThrow(() -> new IllegalArgumentException("Invalid gender ID: " + employeeDto.getGender())).getId();

        employee.setGender(genderId);

        Integer designationId = hrmModulesRepository.findById(employeeDto.getDesignation())
                .orElseThrow(() -> new IllegalArgumentException("Invalid designation ID: " + employeeDto.getDesignation())).getId();

        employee.setDesignation(designationId);

        Integer employeeTypeId = hrmModulesRepository.findById(employeeDto.getEmployeeType())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee type ID: " + employeeDto.getEmployeeType())).getId();

        employee.setEmployeeType(employeeTypeId);
        employee.setReportingTo(employeeDto.getReportingTo());

        employee.setDateOfJoin(employeeDto.getDateOfJoin());
        employee.setLocation(employeeDto.getLocation());
        employee.setSubLocation(employeeDto.getSubLocation());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setFatherName(employeeDto.getFatherName());

        Integer nationalityId = hrmModulesRepository.findById(employeeDto.getNationality())
                .orElseThrow(() -> new IllegalArgumentException("Invalid nationality ID: " + employeeDto.getNationality())).getId();

        employee.setNationality(nationalityId);

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

        Integer userId = userRepository.findByUsername(Constant.NO_LOGIN_USERNAME)
                .orElseThrow(() -> new IllegalArgumentException("User with username '" + Constant.NO_LOGIN_USERNAME + "' not found")).getId();

        employee.setUserId(userId);
        employee.setCreatedBy(employeeDto.getCreatedBy());
        employee.setModifiedBy(employeeDto.getModifiedBy());

        employeeRepository.save(employee);
        return Constant.SUCCESS;
    }

    public EmployeeResponseDto getEmployeeById(Integer employeeId) {

        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        return employeeRepository.findEmployeeById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));
    }

    public String updateEmployee(Integer employeeId, EmployeeUpdateDto employeeDto) {
        return null;
    }

    public String updateEmployeeStatus(Integer employeeId, Integer status) {
        return null;
    }

    public String linkUserToEmployee(Integer employeeId, Integer userId) {
        return null;
    }

    public String unlinkUserFromEmployee(Integer employeeId) {
        return null;
    }
}
