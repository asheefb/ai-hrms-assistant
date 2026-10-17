
package com.asheef.hrms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
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
    private Long reportingTo;

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

    @Column(insertable = false, updatable = false)
    private LocalDateTime created;

    @Column(insertable = false, updatable = false)
    private LocalDateTime modified;
}