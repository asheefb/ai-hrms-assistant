package com.asheef.hrms.service.impl;

import com.asheef.hrms.constants.Constant;
import com.asheef.hrms.constants.ControlModules;
import com.asheef.hrms.entity.HrmModules;
import com.asheef.hrms.entity.StaffingCodeDetails;
import com.asheef.hrms.entity.User;
import com.asheef.hrms.model.dto.StaffingCodeDto;
import com.asheef.hrms.model.dto.StaffingCodeUpdateDto;
import com.asheef.hrms.model.response.HrmModulesResponseProjection;
import com.asheef.hrms.repo.HrmModulesRepository;
import com.asheef.hrms.repo.StaffingCodeRepository;
import com.asheef.hrms.repo.UserRepository;
import com.asheef.hrms.service.StaffingCodeService;
import org.springframework.beans.BeanUtils;
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
    public String updateStaffingCode(Integer id, StaffingCodeUpdateDto dto) {

        StaffingCodeDetails staffingCodeDetails = staffingCodeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(Constant.STAFFING_CODE + Constant.NOT_FOUND));

        if (dto.getManpowerCount() != null && staffingCodeDetails.getManpowerCount() != dto.getManpowerCount()) {
            staffingCodeDetails.setManpowerCount(dto.getManpowerCount());
        }

        if (dto.getBudgetedCtc() != null && staffingCodeDetails.getBudgetedCtc() != dto.getBudgetedCtc()) {
            staffingCodeDetails.setBudgetedCtc(dto.getBudgetedCtc());
        }

        if (dto.getStaffingCode() != null && !dto.getStaffingCode().isEmpty() && !staffingCodeDetails.getStaffingCode().equals(dto.getStaffingCode())) {
            staffingCodeDetails.setStaffingCode(dto.getStaffingCode());
        }

        if (dto.getUnitHead() != null && !staffingCodeDetails.getUnitHead().getId().equals(dto.getUnitHead())) {
            User unitHead = userRepository.findById(dto.getUnitHead())
                    .orElseThrow(() -> new NoSuchElementException(Constant.UNIT_HEAD + Constant.NOT_FOUND));
            staffingCodeDetails.setUnitHead(unitHead);
        }

        if (dto.getHrManager() != null && !staffingCodeDetails.getHrManager().getId().equals(dto.getHrManager())) {
            User hrManager = userRepository.findById(dto.getHrManager())
                    .orElseThrow(() -> new NoSuchElementException(Constant.HR_MANAGER + Constant.NOT_FOUND));
            staffingCodeDetails.setHrManager(hrManager);
        }


        if (dto.getDirApprover() != null && !staffingCodeDetails.getDirApprover().getId().equals(dto.getDirApprover())) {
            User dirApprover = userRepository.findById(dto.getDirApprover())
                    .orElseThrow(() -> new NoSuchElementException(Constant.DIR_APPROVER + Constant.NOT_FOUND));
            staffingCodeDetails.setDirApprover(dirApprover);
        }

        if (dto.getNotes() != null && !dto.getNotes().isEmpty() && !staffingCodeDetails.getNotes().equals(dto.getNotes())) {
            staffingCodeDetails.setNotes(dto.getNotes());
        }

        if (dto.getMembershipRequired() != null && !staffingCodeDetails.getMembershipRequired().equals(dto.getMembershipRequired())) {
            staffingCodeDetails.setMembershipRequired(dto.getMembershipRequired());
        }

        User modifiedBy = userRepository.findById(1)
                .orElseThrow(() -> new NoSuchElementException(Constant.NOT_FOUND));

        staffingCodeDetails.setModifiedBy(modifiedBy);
        staffingCodeRepository.save(staffingCodeDetails);

        return Constant.SUCCESS;
    }

    @Override
    public String activateInactivateStaffingCode(Integer id, String isActive) {

        StaffingCodeDetails staffingCodeDetails = staffingCodeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(Constant.STAFFING_CODE + Constant.NOT_FOUND));

        if (staffingCodeDetails.getIsActive().equals(Integer.parseInt(isActive))) {
            return Constant.STAFFING_CODE + " is already " + (isActive.equals("1") ? Constant.ACTIVATED : Constant.INACTIVATED);
        }

        staffingCodeDetails.setIsActive(isActive.equals("1") ? 1 : 0);
        staffingCodeDetails.setModifiedBy(userRepository.findById(1)
                .orElseThrow(() -> new NoSuchElementException(Constant.NOT_FOUND)));

        staffingCodeRepository.save(staffingCodeDetails);
        return Constant.STAFFING_CODE + " " + (isActive.equals("1") ? Constant.ACTIVATED : Constant.INACTIVATED);
    }

    @Override
    public String approveStaffingCode(Integer id, String status) {
        StaffingCodeDetails staffingCodeDetails = staffingCodeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(Constant.STAFFING_CODE + Constant.NOT_FOUND));

        HrmModulesResponseProjection projection = hrmModulesRepository.findByIdAndCtrlModuleCode(Integer.parseInt(status), ControlModules.STAFFING_STATUS)
                .orElseThrow(() -> new NoSuchElementException(Constant.INVALID + Constant.STATUS));

        HrmModules stcStatus = new HrmModules();
        BeanUtils.copyProperties(projection, stcStatus);
        staffingCodeDetails.setStatus(stcStatus);
        return Constant.STAFFING_CODE + " " + projection.getDescription() + " " + Constant.SUCCESS;
    }
}
