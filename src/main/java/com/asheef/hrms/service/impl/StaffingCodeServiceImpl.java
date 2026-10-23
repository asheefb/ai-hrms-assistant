package com.asheef.hrms.service.impl;

import com.asheef.hrms.constants.Constant;
import com.asheef.hrms.constants.ControlModules;
import com.asheef.hrms.entity.HrmModules;
import com.asheef.hrms.entity.StaffingCodeDetails;
import com.asheef.hrms.entity.User;
import com.asheef.hrms.model.dto.StaffingCodeDto;
import com.asheef.hrms.model.dto.StaffingCodeUpdateDto;
import com.asheef.hrms.repo.HrmModulesRepository;
import com.asheef.hrms.repo.StaffingCodeRepository;
import com.asheef.hrms.repo.UserRepository;
import com.asheef.hrms.service.StaffingCodeService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StaffingCodeServiceImpl implements StaffingCodeService {

    private final StaffingCodeRepository staffingCodeRepository;

    private final UserRepository userRepository;

    private final HrmModulesRepository hrmModulesRepository;


    public StaffingCodeServiceImpl(StaffingCodeRepository staffingCodeRepository, UserRepository userRepository, HrmModulesRepository hrmModulesRepository) {
        this.staffingCodeRepository = staffingCodeRepository;
        this.userRepository = userRepository;
        this.hrmModulesRepository = hrmModulesRepository;
    }


    @Override
    public String createStaffingCode(StaffingCodeDto dto) {
        StaffingCodeDetails staffingCodeDetails = new StaffingCodeDetails();
        staffingCodeDetails.setManpowerCount(dto.getManpowerCount());
        staffingCodeDetails.setBudgetedCtc(dto.getBudgetedCtc());
        staffingCodeDetails.setStaffingCode(dto.getStaffingCode());

        User unitHead = userRepository.findById(dto.getUnitHead())
                .orElseThrow(() -> new NoSuchElementException(Constant.UNIT_HEAD + Constant.NOT_FOUND));

        staffingCodeDetails.setUnitHead(unitHead);

        User hrManager = userRepository.findById(dto.getHrManager())
                .orElseThrow(() -> new NoSuchElementException(Constant.HR_MANAGER + Constant.NOT_FOUND));

        staffingCodeDetails.setHrManager(hrManager);

        User dirApprover = userRepository.findById(dto.getDirApprover())
                .orElseThrow(() -> new NoSuchElementException(Constant.DIR_APPROVER + Constant.NOT_FOUND));

        staffingCodeDetails.setDirApprover(dirApprover);

        staffingCodeDetails.setNotes(dto.getNotes());

        HrmModules status = hrmModulesRepository.findChildByModuleCodes(ControlModules.STAFFING_STATUS, ControlModules.STAFFING_STATUS_IN_PREPARATION)
                .orElseThrow(() -> new NoSuchElementException("Staffing status InPreparation Not Configured."));

        staffingCodeDetails.setStatus(status);

        staffingCodeDetails.setMembershipRequired(dto.getMembershipRequired());

        User createdBy = userRepository.findById(1)
                .orElseThrow(() -> new NoSuchElementException(Constant.NOT_FOUND));

        staffingCodeDetails.setCreatedBy(createdBy);
        staffingCodeDetails.setModifiedBy(createdBy);
        staffingCodeRepository.save(staffingCodeDetails);
        return Constant.SUCCESS;
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
