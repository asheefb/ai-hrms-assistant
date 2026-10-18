package com.asheef.hrms.repo;

import com.asheef.hrms.entity.Employee;
import com.asheef.hrms.model.response.EmployeeResponseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = """
            select\s
                    emp.id as id,
                    emp.emp_number as empNumber,
                    emp.emp_firstname as firstName,
                    emp.emp_lastname as lastName,
                    emp.official_email as officeEmail,
                    emp.personal_email as personalEmail,
                    dept.description as department,
                    gender.description as gender,
                    status.description as status,
                    desg.description as designation,
                    emptype.description as employeeType,
                    concat(reporting.emp_firstname, ' ', reporting.emp_lastname) as reportingTo,
                    CAST(NULLIF(CAST(emp.date_of_join AS CHAR), '0000-00-00') AS DATE) as dateOfJoin,
                    emp.location as location,
                    emp.sub_location as subLocation,
                    CAST(NULLIF(CAST(emp.date_of_birth AS CHAR), '0000-00-00') AS DATE) as dateOfBirth,
                    emp.father_name as fatherName,
                    nationality.description as nationality,
                    emp.country_code as countryCode,
                    emp.contact_number as contactNumber,
                    emp.user_id as userId,
                    createdByUser.name as createdBy,
                    emp.created as created,
                    updatedByUser.name as updatedBy,
                    emp.modified as modified
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
                left join employee reporting on reporting.id = emp.reporting_to
                left join hrm_modules nationalityctrl on nationalityctrl.module_id = 1 AND nationalityctrl.module_code = 'CT03'
                left join hrm_modules nationality on nationality.id = emp.nationality and nationality.module_id = nationalityctrl.id
                left join user createdByUser on createdByUser.id = emp.created_by
                left join user updatedByUser on updatedByUser.id = emp.modified_by
                where emp.id = :employeeId
            """, nativeQuery = true
    )
    Optional<EmployeeResponseProjection> findEmployeeById(@Param("employeeId") Integer employeeId);

    Optional<Employee> findByUserId(Integer userId);

    @Query(value = """
            select\s
                    emp.id as id,
                    emp.emp_number as empNumber,
                    emp.emp_firstname as firstName,
                    emp.emp_lastname as lastName,
                    emp.official_email as officeEmail,
                    emp.personal_email as personalEmail,
                    dept.description as department,
                    gender.description as gender,
                    status.description as status,
                    desg.description as designation,
                    emptype.description as employeeType,
                    concat(reporting.emp_firstname, ' ', reporting.emp_lastname) as reportingTo,
                    CAST(NULLIF(CAST(emp.date_of_join AS CHAR), '0000-00-00') AS DATE) as dateOfJoin,
                    emp.location as location,
                    emp.sub_location as subLocation,
                    CAST(NULLIF(CAST(emp.date_of_birth AS CHAR), '0000-00-00') AS DATE) as dateOfBirth,
                    emp.father_name as fatherName,
                    nationality.description as nationality,
                    emp.country_code as countryCode,
                    emp.contact_number as contactNumber,
                    emp.user_id as userId,
                    createdByUser.name as createdBy,
                    emp.created as created,
                    updatedByUser.name as updatedBy,
                    emp.modified as modified
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
                left join employee reporting on reporting.id = emp.reporting_to
                left join hrm_modules nationalityctrl on nationalityctrl.module_id = 1 AND nationalityctrl.module_code = 'CT03'
                left join hrm_modules nationality on nationality.id = emp.nationality and nationality.module_id = nationalityctrl.id
                left join user createdByUser on createdByUser.id = emp.created_by
                left join user updatedByUser on updatedByUser.id = emp.modified_by
                where emp.status IN (:status)
                  and (
                        (:fieldName = 'empNumber' and emp.emp_number LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'firstName' and emp.emp_firstname LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'lastName' and emp.emp_lastname LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'officeEmail' and emp.official_email LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'personalEmail' and emp.personal_email LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'department' and dept.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'designation' and desg.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'gender' and gender.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'status' and status.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'employeeType' and emptype.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'nationality' and nationality.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'reportingTo' and CONCAT(reporting.emp_firstname, ' ', reporting.emp_lastname) LIKE CONCAT('%', :search, '%'))
                  )
            """,
            countQuery = """
                        select count(*)
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
                        left join employee reporting on reporting.id = emp.reporting_to
                        left join hrm_modules nationalityctrl on nationalityctrl.module_id = 1 AND nationalityctrl.module_code = 'CT03'
                        left join hrm_modules nationality on nationality.id = emp.nationality and nationality.module_id = nationalityctrl.id
                        where emp.status IN (:status)
                          and (
                                (:fieldName = 'empNumber' and emp.emp_number LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'firstName' and emp.emp_firstname LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'lastName' and emp.emp_lastname LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'officeEmail' and emp.official_email LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'personalEmail' and emp.personal_email LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'department' and dept.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'designation' and desg.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'gender' and gender.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'status' and status.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'employeeType' and emptype.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'nationality' and nationality.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'reportingTo' and CONCAT(reporting.emp_firstname, ' ', reporting.emp_lastname) LIKE CONCAT('%', :search, '%'))
                          )
                    """,
            nativeQuery = true
    )
    Page<EmployeeResponseProjection> findAllEmployee(@Param("status") List<Integer> status, @Param("fieldName") String fieldName, @Param("search") String search, Pageable pageable);

    Page<Employee> findAllByStatusIn(List<Integer> status, Pageable paging);

    @Query(value = """
            select\s
                    emp.id as id,
                    emp.emp_number as empNumber,
                    emp.emp_firstname as firstName,
                    emp.emp_lastname as lastName,
                    emp.official_email as officeEmail,
                    emp.personal_email as personalEmail,
                    dept.description as department,
                    gender.description as gender,
                    status.description as status,
                    desg.description as designation,
                    emptype.description as employeeType,
                    concat(reporting.emp_firstname, ' ', reporting.emp_lastname) as reportingTo,
                    CAST(NULLIF(CAST(emp.date_of_join AS CHAR), '0000-00-00') AS DATE) as dateOfJoin,
                    emp.location as location,
                    emp.sub_location as subLocation,
                    CAST(NULLIF(CAST(emp.date_of_birth AS CHAR), '0000-00-00') AS DATE) as dateOfBirth,
                    emp.father_name as fatherName,
                    nationality.description as nationality,
                    emp.country_code as countryCode,
                    emp.contact_number as contactNumber,
                    emp.user_id as userId,
                    createdByUser.name as createdBy,
                    emp.created as created,
                    updatedByUser.name as updatedBy,
                    emp.modified as modified
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
                left join employee reporting on reporting.id = emp.reporting_to
                left join hrm_modules nationalityctrl on nationalityctrl.module_id = 1 AND nationalityctrl.module_code = 'CT03'
                left join hrm_modules nationality on nationality.id = emp.nationality and nationality.module_id = nationalityctrl.id
                left join user createdByUser on createdByUser.id = emp.created_by
                left join user updatedByUser on updatedByUser.id = emp.modified_by
                where (
                        (:fieldName = 'empNumber' and emp.emp_number LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'firstName' and emp.emp_firstname LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'lastName' and emp.emp_lastname LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'officeEmail' and emp.official_email LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'personalEmail' and emp.personal_email LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'department' and dept.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'designation' and desg.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'gender' and gender.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'status' and status.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'employeeType' and emptype.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'nationality' and nationality.description LIKE CONCAT('%', :search, '%'))
                     or (:fieldName = 'reportingTo' and CONCAT(reporting.emp_firstname, ' ', reporting.emp_lastname) LIKE CONCAT('%', :search, '%'))
                  )
            """,
            countQuery = """
                        select count(*)
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
                        left join employee reporting on reporting.id = emp.reporting_to
                        left join hrm_modules nationalityctrl on nationalityctrl.module_id = 1 AND nationalityctrl.module_code = 'CT03'
                        left join hrm_modules nationality on nationality.id = emp.nationality and nationality.module_id = nationalityctrl.id
                        where (
                                (:fieldName = 'empNumber' and emp.emp_number LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'firstName' and emp.emp_firstname LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'lastName' and emp.emp_lastname LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'officeEmail' and emp.official_email LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'personalEmail' and emp.personal_email LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'department' and dept.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'designation' and desg.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'gender' and gender.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'status' and status.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'employeeType' and emptype.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'nationality' and nationality.description LIKE CONCAT('%', :search, '%'))
                             or (:fieldName = 'reportingTo' and CONCAT(reporting.emp_firstname, ' ', reporting.emp_lastname) LIKE CONCAT('%', :search, '%'))
                          )
                    """,
            nativeQuery = true
    )
    Page<EmployeeResponseProjection> findAllEmployeeWithoutStatus(@Param("fieldName") String fieldName, @Param("search") String search, Pageable pageable);
}