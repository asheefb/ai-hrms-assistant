package com.asheef.hrms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_login", columnNames = "login")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "MEDIUMINT UNSIGNED")
    private Integer id;

    @Column(name = "login", nullable = false, length = 155)
    private String login;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "name", nullable = false, length = 155)
    private String name;

    @Column(name = "firstname", length = 155)
    private String firstname;

    @Column(name = "lastname", length = 155)
    private String lastname;

    @Column(name = "title", length = 155)
    private String title;

    @Column(name = "email_work", length = 155)
    private String emailWork;

    @Column(name = "email_home", length = 155)
    private String emailHome;

    @Column(name = "phone_work", length = 155)
    private String phoneWork;

    @Column(name = "phone_home", length = 155)
    private String phoneHome;

    @Column(name = "country_code", nullable = false, length = 5)
    private String countryCode;

    @Column(name = "mobile", nullable = false, length = 155)
    private String mobile;

    @Column(name = "fax", length = 155)
    private String fax;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profil", referencedColumnName = "module_code", nullable = false, columnDefinition = "char(10) not null")
    private CommonModules profil;

    @Column(name = "change_pwd", nullable = false, length = 1)
    private String changePwd = "0";

    @Column(name = "is_employee", nullable = false, length = 1)
    private String isEmployee = "0";

    @Column(name = "landing_page")
    private Integer landingPage = 0;

    @Column(name = "limited_access")
    private Integer limitedAccess = 0;

    @Column(nullable = false, length = 3)
    private String timezone = "5.5";

    private LocalDate dob;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false, columnDefinition = "MEDIUMINT")
    private CommonModules type;

    @Column(name = "reference_id")
    private Integer referenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", columnDefinition = "MEDIUMINT UNSIGNED", foreignKey = @ForeignKey(name = "fk_staffing_created_by"))
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by", columnDefinition = "MEDIUMINT UNSIGNED", foreignKey = @ForeignKey(name = "fk_staffing_modified_by"))
    private User modifiedBy;

    @CreationTimestamp
    @Column(name = "created", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "modified", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modified;
}