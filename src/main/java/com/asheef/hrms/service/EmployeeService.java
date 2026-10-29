package com.asheef.hrms.service;

import com.asheef.hrms.commonhelper.Helper;
import com.asheef.hrms.constants.Constant;
import com.asheef.hrms.constants.ControlModules;
import com.asheef.hrms.entity.Employee;
import com.asheef.hrms.entity.MobileCountryCodes;
import com.asheef.hrms.entity.User;
import com.asheef.hrms.model.dto.EmployeeListRequestDto;
import com.asheef.hrms.model.dto.EmployeeRequestDto;
import com.asheef.hrms.model.dto.EmployeeUpdateDto;
import com.asheef.hrms.model.response.EmployeeResponseDto;
import com.asheef.hrms.model.response.EmployeeResponseProjection;
import com.asheef.hrms.repo.EmployeeRepository;
import com.asheef.hrms.repo.HrmModulesRepository;
import com.asheef.hrms.repo.MobileCountryCodesRepository;
import com.asheef.hrms.repo.UserRepository;
import com.asheef.hrms.utils.SortUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

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

        Integer deptId = hrmModulesRepository.findByIdAndCtrlModuleCode(employeeDto.getDepartment(), ControlModules.DEPARTMENT)
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_DEPARTMENT + employeeDto.getDepartment())).getId();

        employee.setDepartment(deptId);

        Integer activeStatus = hrmModulesRepository.findChildByModuleCodes(ControlModules.STATUS_OF_EMPLOYMENT, "ST01")
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_ACTIVE_STATUS)).getId();

        employee.setStatus(activeStatus);

        Integer genderId = hrmModulesRepository.findByIdAndCtrlModuleCode(employeeDto.getGender(), ControlModules.GENDER)
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_GENDER)).getId();

        employee.setGender(genderId);

        Integer designationId = hrmModulesRepository.findByIdAndCtrlModuleCode(employeeDto.getDesignation(), ControlModules.DESIGNATION)
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_DESIGNATION)).getId();

        employee.setDesignation(designationId);

        Integer employeeTypeId = hrmModulesRepository.findByIdAndCtrlModuleCode(employeeDto.getEmployeeType(), ControlModules.EMPLOYEE_TYPE)
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_EMPLOYEE_TYPE)).getId();

        employee.setEmployeeType(employeeTypeId);
        employee.setReportingTo(employeeDto.getReportingTo());

        employee.setDateOfJoin(employeeDto.getDateOfJoin());
        employee.setLocation(employeeDto.getLocation());
        employee.setSubLocation(employeeDto.getSubLocation());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setFatherName(employeeDto.getFatherName());

        Integer nationalityId = hrmModulesRepository.findByIdAndCtrlModuleCode(employeeDto.getNationality(), ControlModules.NATIONALITY)
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_NATIONALITY)).getId();

        employee.setNationality(nationalityId);

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
            employee.setContactNumber(employeeDto.getContactNumber());
        }

        Integer userId = userRepository.findByLogin(Constant.NO_LOGIN)
                .orElseThrow(() -> new IllegalArgumentException(Constant.USER_NAME + Constant.NO_LOGIN + Constant.NOT_FOUND)).getId();

        employee.setUserId(userId);
        employee.setCreatedBy(1);
        employee.setModifiedBy(1);

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
            Integer deptId = hrmModulesRepository.findByIdAndCtrlModuleCode(employeeDto.getDepartment(), ControlModules.DEPARTMENT)
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_DEPARTMENT)).getId();
            employee.setDepartment(deptId);
        }

        if (employeeDto.getGender() != null && !employeeDto.getGender().equals(employee.getGender())) {
            Integer genderId = hrmModulesRepository.findByIdAndCtrlModuleCode(employeeDto.getGender(), ControlModules.GENDER)
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_GENDER)).getId();
            employee.setGender(genderId);
        }

        if (employeeDto.getStatus() != null && !employeeDto.getStatus().equals(employee.getStatus())) {
            Integer statusId = hrmModulesRepository.findByIdAndCtrlModuleCode(employeeDto.getStatus(), ControlModules.STATUS_OF_EMPLOYMENT)
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_STATUS)).getId();
            employee.setStatus(statusId);
        }

        if (employeeDto.getDesignation() != null && !employeeDto.getDesignation().equals(employee.getDesignation())) {
            Integer designationId = hrmModulesRepository.findByIdAndCtrlModuleCode(employeeDto.getDesignation(), ControlModules.DESIGNATION)
                    .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_DESIGNATION)).getId();
            employee.setDesignation(designationId);
        }


        if (employeeDto.getEmployeeType() != null && !employeeDto.getEmployeeType().equals(employee.getEmployeeType())) {
            Integer employeeTypeId = hrmModulesRepository.findByIdAndCtrlModuleCode(employeeDto.getEmployeeType(), ControlModules.EMPLOYEE_TYPE)
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
            Integer nationalityId = hrmModulesRepository.findByIdAndCtrlModuleCode(employeeDto.getNationality(), ControlModules.NATIONALITY)
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
            employee.setContactNumber(employeeDto.getContactNumber());
        }

        employee.setModifiedBy(employeeDto.getModifiedBy());

        employeeRepository.save(employee);

        return Constant.SUCCESS;
    }

    public String updateEmployeeStatus(Integer employeeId, Integer status) {

        if (employeeId == null) {
            throw new IllegalArgumentException(Constant.EMPLOYEE_ID_REQUIRED);
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(Constant.EMPLOYEE_NOT_FOUND));

        if (status == null)
            throw new IllegalArgumentException(Constant.INVALID_STATUS);

        Integer statusId = hrmModulesRepository.findByIdAndCtrlModuleCode(status, ControlModules.STATUS_OF_EMPLOYMENT)
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_STATUS)).getId();

        employee.setStatus(statusId);
        employee.setModifiedBy(employee.getModifiedBy());

        employeeRepository.save(employee);
        return Constant.SUCCESS;
    }

    @Transactional
    public String linkUserToEmployee(Integer employeeId, Integer userId) {

        if (employeeId == null)
            throw new IllegalArgumentException(Constant.EMPLOYEE_ID_REQUIRED);

        if (userId == null)
            throw new IllegalArgumentException(Constant.USER_ID_REQUIRED);

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(Constant.EMPLOYEE_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(Constant.INVALID_USER_ID));

        if (employeeRepository.findByUserId(user.getId()).isPresent()) {
            throw new IllegalArgumentException(Constant.ALREADY_LINKED_USER);
        }

        user.setFirstname(employee.getEmpFirstname());
        user.setLastname(employee.getEmpLastname());
        String fullName = ((employee.getEmpFirstname() == null ? "" : employee.getEmpFirstname()) + " "
                + (employee.getEmpLastname() == null ? "" : employee.getEmpLastname())).trim();
        if (!fullName.isEmpty()) {
            user.setName(fullName);
        }
        user.setEmailWork(employee.getOfficialEmail());
        user.setEmailHome(employee.getPersonalEmail());
        if (employee.getCountryCode() != null) {
            user.setCountryCode(employee.getCountryCode());
        }
        if (employee.getContactNumber() != null) {
            user.setMobile(employee.getContactNumber());
        }
        user.setDob(employee.getDateOfBirth());
        user.setIsEmployee("1");

        employee.setUserId(user.getId());
        employee.setModifiedBy(1);

        userRepository.save(user);
        employeeRepository.save(employee);

        return Constant.SUCCESS;
    }

    public String unlinkUserFromEmployee(Integer employeeId) {

        if (employeeId == null)
            throw new IllegalArgumentException(Constant.EMPLOYEE_ID_REQUIRED);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(Constant.EMPLOYEE_NOT_FOUND));

        Integer noLoginUserId = userRepository.findByLogin(Constant.NO_LOGIN)
                .orElseThrow(() -> new IllegalArgumentException(Constant.USER_NAME + Constant.NO_LOGIN + Constant.NOT_FOUND)).getId();

        employee.setUserId(noLoginUserId);
        employee.setModifiedBy(1);

        employeeRepository.save(employee);

        return Constant.SUCCESS;
    }

    public Page<EmployeeResponseDto> getAllEmployees(EmployeeListRequestDto dto) {

        List<Sort.Order> orders = SortUtil.getSortValues(dto.getSortBy());

        int pageNo = dto.getPageNumber() > 0
                ? dto.getPageNumber() - 1
                : 0;

        Pageable paging = PageRequest.of(
                pageNo,
                dto.getPageSize(),
                Sort.by(orders)
        );

        String fieldName = dto.getFieldName(), search = dto.getSearch();
        List<Integer> status = dto.getStatus();

        boolean hasFieldName = fieldName != null && !fieldName.isBlank();
        boolean hasSearch = search != null && !search.isBlank();

        if (hasFieldName != hasSearch) {
            throw new IllegalArgumentException("Both field name and search value must be provided together.");
        }

        if (!hasFieldName) {

            Page<Employee> employees;

            if (status == null || status.isEmpty()) {
                employees = employeeRepository.findAll(paging);
            } else {
                employees = employeeRepository.findAllByStatusIn(status, paging);
            }

            return employees.map(Helper::getEmployeeResponseDto);
        }

        List<String> allowedFields = List.of(
                "empNumber",
                "firstName",
                "lastName",
                "officeEmail",
                "personalEmail",
                "reportingTo"
        );

        if (!allowedFields.contains(fieldName)) {
            throw new IllegalArgumentException("Unsupported search field: " + fieldName);
        }

        Page<EmployeeResponseProjection> employeePage;

        if (status == null || status.isEmpty())
            employeePage = employeeRepository.findAllEmployeeWithoutStatus(fieldName, search.trim(), paging);
        else
            employeePage = employeeRepository.findAllEmployee(status, fieldName, search.trim(), paging);


        return employeePage.map(Helper::getEmployeeResponseDto);
    }
}
