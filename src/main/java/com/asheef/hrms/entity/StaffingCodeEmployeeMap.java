package com.asheef.hrms.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "staffing_code_employee_map",
        uniqueConstraints = {@UniqueConstraint(name = "uniq_staffing_code_employee", columnNames = {"manpower_id", "employee_id"})},
        indexes = {@Index(name = "idx_staffing_code_employee", columnList = "manpower_id, employee_id")}
)
public class StaffingCodeEmployeeMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manpower_id", referencedColumnName = "id", nullable = false, columnDefinition = "MEDIUMINT", foreignKey = @ForeignKey(name = "fk_staff_emp_map_manpower_id"))
    private StaffingCodeDetails staffingCodeDetails;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false, columnDefinition = "INT UNSIGNED", foreignKey = @ForeignKey(name = "fk_staff_emp_map_employee_id"))
    private Employee employee;

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Integer isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", columnDefinition = "MEDIUMINT UNSIGNED", foreignKey = @ForeignKey(name = "fk_staff_emp_map_cb"))
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by", columnDefinition = "MEDIUMINT UNSIGNED",foreignKey = @ForeignKey(name = "fk_staff_emp_map_mb"))
    private User modifiedBy;

    @CreationTimestamp
    @Column(name = "created", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "modified", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modified;
}
