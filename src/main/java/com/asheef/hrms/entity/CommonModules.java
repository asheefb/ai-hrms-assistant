package com.asheef.hrms.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {@Index(name = "idx_module_id_code", columnList = "module_id, module_code")},
        uniqueConstraints = {@UniqueConstraint(name = "uk_common_modules_ref_module", columnNames = "ref_module")}
)
public class CommonModules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "MEDIUMINT")
    private Integer id;

    @Column(nullable = false)
    private Integer moduleId;

    @Column(name = "module_code", columnDefinition = "char(10) not null")
    private String moduleCode;

    @Column(columnDefinition = "varchar(200) not null")
    private String description;

    @Column(name = "ref_module", columnDefinition = "varchar(50) default null")
    private String refModule;

    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean isActive;

    private Integer createdBy;

    private Integer modifiedBy;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(updatable = true, columnDefinition = "timestamp default current_timestamp on update current_timestamp")
    private LocalDateTime modified;
}
