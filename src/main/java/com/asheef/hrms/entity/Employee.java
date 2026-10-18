
package com.asheef.hrms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniq_emp_number", columnNames = {"emp_number"})},
        indexes = {@Index(name = "idx_user_id", columnList = "user_id"),
                @Index(name = "idx_reporting_to", columnList = "reporting_to"),
                @Index(name = "idx_employee_status", columnList = "status")})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    @Column(name = "emp_number", nullable = false, length = 20)
    private String empNumber;

    @Column(name = "emp_firstname", length = 50)
    private String empFirstname;

    @Column(name = "emp_lastname", length = 50)
    private String empLastname;

    @Column(name = "official_email", length = 50)
    private String officialEmail;

    @Column(name = "personal_email", length = 50)
    private String personalEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department", nullable = false)
    private HrmModules department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private HrmModules status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender", nullable = false)
    private HrmModules gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designation", nullable = false)
    private HrmModules designation;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_type", nullable = false)
    private HrmModules employeeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nationality", nullable = false)
    private HrmModules nationality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_to")
    private Employee reportingManager;

    @Column(name = "date_of_join", nullable = false)
    private LocalDate dateOfJoin;

    @Column(name = "location")
    private Integer location;

    @Column(name = "sub_location")
    private Integer subLocation;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "father_name", length = 60)
    private String fatherName;

    @Column(name = "country_code", length = 5)
    private String countryCode;

    @Column(name = "contact_number", length = 30)
    private String contactNumber;

    private Integer userId;

    @Column(name = "created_by", nullable = false,
            columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer createdBy;

    @Column(name = "modified_by", nullable = false,
            columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer modifiedBy;

    @CreationTimestamp
    @Column(name = "created", updatable = false,
            columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "modified",
            columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private LocalDateTime modified;
}