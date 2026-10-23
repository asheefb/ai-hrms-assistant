package com.asheef.hrms.repo;

import com.asheef.hrms.entity.StaffingCodeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffingCodeRepository extends JpaRepository<StaffingCodeDetails, Integer> {
}
