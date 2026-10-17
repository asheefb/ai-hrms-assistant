package com.asheef.hrms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniq_country_code", columnNames = {"country_code"})})
public class MobileCountryCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(50) not null")
    private String countryName;

    @Column(columnDefinition = "varchar(10) not null")
    private String countryCode;

    @Column(nullable = false)
    private Integer numberLength;

    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean isActive;

    private Integer createdBy;

    private Integer modifiedBy;

    @CreationTimestamp
    @Column(updatable = false ,columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(updatable = true, columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private LocalDateTime modifiedAt;
}
