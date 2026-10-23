package com.asheef.hrms.service;

import com.asheef.hrms.model.dto.StaffingCodeDto;
import com.asheef.hrms.model.dto.StaffingCodeUpdateDto;

public interface StaffingCodeService {

    String createStaffingCode(StaffingCodeDto staffingCodeDto);

    String updateStaffingCode(Integer id, StaffingCodeUpdateDto staffingCodeDto);

    String activateInactivateStaffingCode(Integer id, String isActive);

    String approveStaffingCode(Integer id, String status);
}
