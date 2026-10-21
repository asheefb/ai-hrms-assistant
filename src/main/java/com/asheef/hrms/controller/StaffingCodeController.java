package com.asheef.hrms.controller;

import com.asheef.hrms.model.dto.StaffingCodeDto;
import com.asheef.hrms.model.dto.StaffingCodeListDto;
import com.asheef.hrms.model.dto.StaffingCodeUpdateDto;
import com.asheef.hrms.service.StaffingCodeService;
import com.asheef.hrms.utils.ResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staffing-codes")
public class StaffingCodeController {

    public final StaffingCodeService staffingCodeService;

    public StaffingCodeController(StaffingCodeService staffingCodeService) {
        this.staffingCodeService = staffingCodeService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createStaffingCode(@RequestBody StaffingCodeDto staffingCodeDto) {

        String msg = staffingCodeService.createStaffingCode(staffingCodeDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(Boolean.TRUE, HttpStatus.CREATED.value(), msg));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateStaffingCode(@PathVariable String id, @RequestBody StaffingCodeUpdateDto staffingCodeDto) {
        String msg = staffingCodeService.updateStaffingCode(id, staffingCodeDto);
        return ResponseEntity.
                ok(new ResponseDto(Boolean.TRUE, HttpStatus.OK.value(), msg));
    }

    @PatchMapping("/activate-inactivate/{id}")
    public ResponseEntity<ResponseDto> activateInactivateStaffingCode(@PathVariable String id, @RequestParam("isActive") String isActive) {
        String msg = staffingCodeService.activateInactivateStaffingCode(id, isActive);
        return ResponseEntity.
                ok(new ResponseDto(Boolean.TRUE, HttpStatus.OK.value(), msg));
    }

    @PatchMapping("/approval/{id}")
    public ResponseEntity<ResponseDto> approveStaffingCode(@PathVariable String id, @RequestParam("status") String status) {
        String msg = staffingCodeService.approveStaffingCode(id, status);
        return ResponseEntity.
                ok(new ResponseDto(Boolean.TRUE, HttpStatus.OK.value(), msg));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getStaffingCodeById(@PathVariable String id) {
        
        return ResponseEntity.
                ok(new ResponseDto(Boolean.TRUE, HttpStatus.OK.value(), "msg"));
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllStaffingCodes(@ModelAttribute StaffingCodeListDto staffingCodeListDto) {
        return ResponseEntity.
                ok(new ResponseDto(Boolean.TRUE, HttpStatus.OK.value(), "msg"));
    }
}
