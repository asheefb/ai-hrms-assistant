package com.asheef.hrms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "staffingcode_details", indexes = {
        @Index(name = "Idx_manpower_status_id", columnList = "status_id")})
public class StaffingCodeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "MEDIUMINT")
    private Integer id;

    @Column(name = "manpower_count", nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private Integer manpowerCount = 1;

    @Column(name = "budgeted_ctc")
    private Integer budgetedCtc;

    @Column(name = "staffing_code", length = 20)
    private String staffingCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_head", nullable = false)
    private User unitHead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hr_manager", nullable = false)
    private User hrManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dir_approver", nullable = false)
    private User dirApprover;

    @Column(name = "notes", length = 255)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false, columnDefinition = "MEDIUMINT")
    private CommonModules status;

    @Column(name = "is_active", nullable = false, columnDefinition = "MEDIUMINT DEFAULT 1")
    private Integer isActive = 1;

    @Column(name = "membership_required", columnDefinition = "TINYINT DEFAULT 0")
    private Integer membershipRequired = 0;

    @Column(name = "created_by", columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer createdBy;

    @Column(name = "modified_by", columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer modifiedBy;

    @CreationTimestamp
    @Column(name = "created", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "modified", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modified;
}