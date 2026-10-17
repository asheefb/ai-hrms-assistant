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

    /*
    select 
        emp.id,
        emp.emp_number,
        emp.emp_firstname,
        emp.emp_lastname,
        emp.official_email,
        emp.personal_email,
        dept.description as department,
        gender.description as gender,
        status.description as status,
        desg.description as designation,
        emptype.description as employee_type,
        concat(reporting.emp_firstname, ' ', reporting.emp_lastname) as reporting_to,
        emp.date_of_join,
        emp.location,
        emp.sub_location,
        emp.date_of_birth,
        emp.father_name,
        nationality.description as nationality,
        emp.country_code,
        emp.contact_number,
        emp.user_id,
        createdByUser.name as created_by,
        emp.created,
        updatedByUser.name as updated_by,
        emp.updated
    from employee emp
    left join hrm_modules deptctrl on deptctrl.module_id = 1 AND deptctrl.module_code = 'CT02'
    left join hrm_modules dept on dept.id = emp.department and dept.module_id = deptctrl.id
    left join hrm_modules genderctrl on genderctrl.module_id = 1 AND genderctrl.module_code = 'CT04'
    left join hrm_modules gender on gender.id = emp.gender and gender.module_id = genderctrl.id
    left join hrm_modules statusctrl on statusctrl.module_id = 1 AND statusctrl.module_code = 'CT01'
    left join hrm_modules status on status.id = emp.status and status.module_id = statusctrl.id
    left join hrm_modules desgctrl on desgctrl.module_id = 1 AND desgctrl.module_code = 'CT05'
    left join hrm_modules desg on desg.id = emp.designation and desg.module_id = desgctrl.id
    left join hrm_modules emptypectrl on emptypectrl.module_id = 1 AND emptypectrl.module_code = 'CT06'
    left join hrm_modules emptype on emptype.id = emp.employee_type and emptype.module_id = emptypectrl.id
    left join hrm_employee reporting on reporting.id = emp.reporting_to
    left join hrm_modules nationalityctrl on nationalityctrl.module_id = 1 AND nationalityctrl.module_code = 'CT03'
    left join hrm_modules nationality on nationality.id = emp.nationality and nationality.module_id = nationalityctrl.id
    left join user createdByUser on createdByUser.id = emp.created_by
    left join user updatedByUser on updatedByUser.id = emp.modified_by
    where emp.id = :employeeId

    */

}
