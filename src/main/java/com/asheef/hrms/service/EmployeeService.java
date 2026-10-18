package com.asheef.hrms.service;

import com.asheef.hrms.commonhelper.Helper;
import com.asheef.hrms.constants.Constant;
import com.asheef.hrms.entity.Employee;
import com.asheef.hrms.entity.MobileCountryCodes;
import com.asheef.hrms.model.dto.EmployeeRequestDto;
import com.asheef.hrms.model.dto.EmployeeUpdateDto;
import com.asheef.hrms.model.response.EmployeeResponseDto;
import com.asheef.hrms.model.response.EmployeeResponseProjection;
import com.asheef.hrms.repo.EmployeeRepository;
import com.asheef.hrms.repo.HrmModulesRepository;
import com.asheef.hrms.repo.MobileCountryCodesRepository;
import com.asheef.hrms.repo.UserRepository;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_DEPARTMENT + employeeDto.getDepartment())).getId();

        employee.setDepartment(deptId);

        Integer activeStatus = hrmModulesRepository.findChildByModuleCodes("CT02", "ST01")
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_ACTIVE_STATUS)).getId();

        employee.setStatus(activeStatus);

        Integer genderId = hrmModulesRepository.findById(employeeDto.getGender())
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_GENDER)).getId();

        employee.setGender(genderId);

        Integer designationId = hrmModulesRepository.findById(employeeDto.getDesignation())
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_DESIGNATION)).getId();

        employee.setDesignation(designationId);

        Integer employeeTypeId = hrmModulesRepository.findById(employeeDto.getEmployeeType())
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_EMPLOYEE_TYPE)).getId();

        employee.setEmployeeType(employeeTypeId);
        employee.setReportingTo(employeeDto.getReportingTo());

        employee.setDateOfJoin(employeeDto.getDateOfJoin());
        employee.setLocation(employeeDto.getLocation());
        employee.setSubLocation(employeeDto.getSubLocation());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setFatherName(employeeDto.getFatherName());

        Integer nationalityId = hrmModulesRepository.findById(employeeDto.getNationality())
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_NATIONALITY)).getId();

        employee.setNationality(nationalityId);

        if (employeeDto.getCountryCode() != null && !employeeDto.getCountryCode().isEmpty() &&
                (employeeDto.getContactNumber() == null || employeeDto.getContactNumber().isEmpty()))
            throw new IllegalArgumentException(Constant.CONTACT_NUMBER_REQUIRED);

        else if ((employeeDto.getCountryCode() == null || employeeDto.getCountryCode().isEmpty()) &&
                (employeeDto.getContactNumber() != null && !employeeDto.getContactNumber().isEmpty()))
            throw new IllegalArgumentException(Constant.COUNTRY_CODE_REQUIRED);
        else if (employeeDto.getCountryCode() != null && !employeeDto.getCountryCode().isEmpty()) {
            MobileCountryCodes mobileCountryCodes = mobileCountryCodesRepository.findByCountryCode(employeeDto.getCountryCode())
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_COUNTRY_CODE));

            if (employeeDto.getContactNumber().length() != mobileCountryCodes.getNumberLength()) {
                throw new IllegalArgumentException(Constant.INVALID_CONTACT_NUMBER_LENGTH);
            }

            employee.setCountryCode(mobileCountryCodes.getCountryCode());
        }

        employee.setContactNumber(employeeDto.getContactNumber());

        Integer userId = userRepository.findByUsername(Constant.NO_LOGIN_USERNAME)
                .orElseThrow(() -> new IllegalArgumentException(Constant.USER_NAME + Constant.NO_LOGIN_USERNAME + Constant.NOT_FOUND)).getId();

        employee.setUserId(userId);
        employee.setCreatedBy(employeeDto.getCreatedBy());
        employee.setModifiedBy(employeeDto.getModifiedBy());

        employeeRepository.save(employee);
        return Constant.SUCCESS;
    }

    public EmployeeResponseDto getEmployeeById(Integer employeeId) {

        if (employeeId == null) {
            throw new IllegalArgumentException(Constant.INVALID_EMPLOYEE);
        }

        EmployeeResponseProjection employeeResponseProjection = employeeRepository.findEmployeeById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(Constant.EMPLOYEE_NOT_FOUND + employeeId));

        return Helper.getEmployeeResponseDto(employeeResponseProjection);
    }

    public String updateEmployee(Integer employeeId, EmployeeUpdateDto employeeDto) {

        if (employeeId == null) {
            throw new IllegalArgumentException(Constant.EMPLOYEE_ID_REQUIRED);
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(Constant.EMPLOYEE_NOT_FOUND));

        if (employeeDto.getFirstName() != null && !employeeDto.getFirstName().isEmpty() && !employeeDto.getFirstName().equals(employee.getEmpFirstname())) {

            if (employeeDto.getFirstName().length() > 50) {
                throw new IllegalArgumentException(Constant.FIRST_NAME_TOO_LONG);
            }
            employee.setEmpFirstname(employeeDto.getFirstName());
        }

        if (employeeDto.getLastName() != null && !employeeDto.getLastName().isEmpty() && !employeeDto.getLastName().equals(employee.getEmpLastname())) {
            if (employeeDto.getLastName().length() > 50) {
                throw new IllegalArgumentException(Constant.LAST_NAME_TOO_LONG);
            }
            employee.setEmpLastname(employeeDto.getLastName());
        }

        if (employeeDto.getOfficeEmail() != null && !employeeDto.getOfficeEmail().isEmpty() && !employeeDto.getOfficeEmail().equals(employee.getOfficialEmail())) {
            employee.setOfficialEmail(employeeDto.getOfficeEmail());
        }

        if (employeeDto.getPersonalEmail() != null && !employeeDto.getPersonalEmail().isEmpty() && !employeeDto.getPersonalEmail().equals(employee.getPersonalEmail())) {
            employee.setPersonalEmail(employeeDto.getPersonalEmail());
        }

        if (employeeDto.getDepartment() != null && !employeeDto.getDepartment().equals(employee.getDepartment())) {
            Integer deptId = hrmModulesRepository.findById(employeeDto.getDepartment())
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_DEPARTMENT)).getId();
            employee.setDepartment(deptId);
        }

        if (employeeDto.getGender() != null && !employeeDto.getGender().equals(employee.getGender())) {
            Integer genderId = hrmModulesRepository.findById(employeeDto.getGender())
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_GENDER)).getId();
            employee.setGender(genderId);
        }

        if (employeeDto.getStatus() != null && !employeeDto.getStatus().equals(employee.getStatus())) {
            Integer statusId = hrmModulesRepository.findById(employeeDto.getStatus())
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_STATUS)).getId();
            employee.setStatus(statusId);
        }

        if (employeeDto.getDesignation() != null && !employeeDto.getDesignation().equals(employee.getDesignation())) {
            Integer designationId = hrmModulesRepository.findById(employeeDto.getDesignation())
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_DESIGNATION)).getId();
            employee.setDesignation(designationId);
        }


        if (employeeDto.getEmployeeType() != null && !employeeDto.getEmployeeType().equals(employee.getEmployeeType())) {
            Integer employeeTypeId = hrmModulesRepository.findById(employeeDto.getEmployeeType())
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_EMPLOYEE_TYPE)).getId();
            employee.setEmployeeType(employeeTypeId);
        }

        if (employeeDto.getReportingTo() != null && !employeeDto.getReportingTo().equals(employee.getReportingTo()))
            employee.setReportingTo(employeeDto.getReportingTo());

        if (employeeDto.getDateOfJoin() != null && !employeeDto.getDateOfJoin().equals(employee.getDateOfJoin()))
            employee.setDateOfJoin(employeeDto.getDateOfJoin());

        if (employeeDto.getLocation() != null && !employeeDto.getLocation().equals(employee.getLocation()))
            employee.setLocation(employeeDto.getLocation());

        if (employeeDto.getSubLocation() != null && !employeeDto.getSubLocation().equals(employee.getSubLocation()))
            employee.setSubLocation(employeeDto.getSubLocation());

        if (employeeDto.getDateOfBirth() != null && !employeeDto.getDateOfBirth().equals(employee.getDateOfBirth()))
            employee.setDateOfBirth(employeeDto.getDateOfBirth());

        if (employeeDto.getFatherName() != null && !employeeDto.getFatherName().isEmpty() && !employeeDto.getFatherName().equals(employee.getFatherName())) {
            if (employeeDto.getFatherName().length() > 60) {
                throw new IllegalArgumentException(Constant.FATHER_NAME_TOO_LONG);
            }
            employee.setFatherName(employeeDto.getFatherName());
        }

        if (employeeDto.getNationality() != null && !employeeDto.getNationality().equals(employee.getNationality())) {
            Integer nationalityId = hrmModulesRepository.findById(employeeDto.getNationality())
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_NATIONALITY)).getId();
            employee.setNationality(nationalityId);
        }

        if (employeeDto.getCountryCode() != null && !employeeDto.getCountryCode().isEmpty() &&
                (employeeDto.getContactNumber() == null || employeeDto.getContactNumber().isEmpty())) {
            throw new IllegalArgumentException(Constant.CONTACT_NUMBER_REQUIRED);
        } else if ((employeeDto.getCountryCode() == null || employeeDto.getCountryCode().isEmpty()) &&
                (employeeDto.getContactNumber() != null && !employeeDto.getContactNumber().isEmpty())) {
            throw new IllegalArgumentException(Constant.COUNTRY_CODE_REQUIRED);
        } else if (employeeDto.getCountryCode() != null && !employeeDto.getCountryCode().isEmpty()) {
            MobileCountryCodes mobileCountryCodes = mobileCountryCodesRepository.findByCountryCode(employeeDto.getCountryCode())
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_COUNTRY_CODE));

            if (employeeDto.getContactNumber().length() != mobileCountryCodes.getNumberLength()) {
                throw new IllegalArgumentException(Constant.INVALID_CONTACT_NUMBER_LENGTH);
            }

            employee.setCountryCode(mobileCountryCodes.getCountryCode());
        }


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
