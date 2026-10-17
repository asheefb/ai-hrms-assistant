
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

    @Column(nullable = false, unique = true, length = 20)
    private String empNumber;

    @Column(length = 50)
    private String empFirstname;

    @Column(length = 50)
    private String empLastname;

    @Column(length = 50)
    private String officialEmail;

    @Column(length = 50)
    private String personalEmail;

    @Column(nullable = false)
    private Integer department;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    private Integer gender;

    @Column(nullable = false)
    private Integer designation;

    @Column(nullable = false)
    private Integer employeeType;

    @Column(nullable = false,
            columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Integer reportingTo;

    @Column(nullable = false)
    private LocalDate dateOfJoin;

    private Integer location;

    private Integer subLocation;

    private LocalDate dateOfBirth;

    @Column(length = 60)
    private String fatherName;

    @Column(nullable = false)
    private Integer nationality;

    @Column(length = 5)
    private String countryCode;

    @Column(length = 30)
    private String contactNumber;

    private Integer userId;

    @Column(nullable = false,
            columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer createdBy;

    @Column(nullable = false,
            columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer modifiedBy;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(updatable = true, columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private LocalDateTime modified;
}