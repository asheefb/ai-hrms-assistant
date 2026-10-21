package com.asheef.hrms.service.impl;

import com.asheef.hrms.model.dto.StaffingCodeDto;
import com.asheef.hrms.model.dto.StaffingCodeUpdateDto;
import com.asheef.hrms.service.StaffingCodeService;
import org.springframework.stereotype.Service;

@Service
public class StaffingCodeServiceImpl implements StaffingCodeService {


    @Override
    public String createStaffingCode(StaffingCodeDto staffingCodeDto) {
        return "";
    }

    @Override
    public String updateStaffingCode(String id, StaffingCodeUpdateDto staffingCodeDto) {
        return "";
    }

    @Override
    public String activateInactivateStaffingCode(String id, String isActive) {
        return "";
    }

    @Override
    public String approveStaffingCode(String id, String status) {
        return "";
    }
}
