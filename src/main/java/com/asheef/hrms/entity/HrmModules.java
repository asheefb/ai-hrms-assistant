package com.asheef.hrms.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = {@Index(name = "idx_module_id_code", columnList = "module_id, module_code")})
public class HrmModules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer moduleId;

    @Column(columnDefinition = "char(5) not null")
    private String moduleCode;

    @Column(columnDefinition = "varchar(100) not null")
    private String description;

    @Column(nullable = false, columnDefinition = "tinyint default 1")
    private Boolean isActive;

    private Integer createdBy;

    private Integer modifiedBy;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(updatable = true)
    private LocalDateTime modifiedAt;
}
