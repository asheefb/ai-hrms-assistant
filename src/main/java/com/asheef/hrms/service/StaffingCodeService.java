package com.asheef.hrms.service;

import com.asheef.hrms.model.dto.StaffingCodeDto;
import com.asheef.hrms.model.dto.StaffingCodeUpdateDto;

public interface StaffingCodeService {

    String createStaffingCode(StaffingCodeDto staffingCodeDto);

    String updateStaffingCode(String id, StaffingCodeUpdateDto staffingCodeDto);

    String activateInactivateStaffingCode(String id, String isActive);

    String approveStaffingCode(String id, String status);
}
