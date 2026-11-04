package com.asheef.hrms.repo;

import com.asheef.hrms.entity.StaffingCodeEmployeeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffingCodeEmployeeMapRepository extends JpaRepository<StaffingCodeEmployeeMap, Integer> {

    boolean existsByStaffingCodeDetails_IdAndIsActive(Integer staffingCodeId, Integer isActive);

    Optional<StaffingCodeEmployeeMap> findFirstByEmployee_IdAndIsActiveOrderByIdDesc(Integer employeeId, Integer isActive);

    Optional<StaffingCodeEmployeeMap> findFirstByStaffingCodeDetails_IdOrderByIdDesc(Integer staffingCodeId);
}
