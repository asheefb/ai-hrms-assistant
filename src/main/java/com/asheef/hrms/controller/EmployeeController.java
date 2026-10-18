package com.asheef.hrms.controller;

import com.asheef.hrms.entity.Employee;
import com.asheef.hrms.model.dto.EmployeeRequestDto;
import com.asheef.hrms.model.dto.EmployeeUpdateDto;
import com.asheef.hrms.model.response.EmployeeResponseDto;
import com.asheef.hrms.service.EmployeeService;
import com.asheef.hrms.utils.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> onboardEmployee(@Valid @RequestBody EmployeeRequestDto employee) {

        String msg = employeeService.onboardEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(Boolean.TRUE, HttpStatus.CREATED.value(), msg));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getEmployeeById(@PathVariable Integer id) {
        EmployeeResponseDto employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(new ResponseDto(Boolean.TRUE, HttpStatus.OK.value(), employee, "Employee found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateEmployee(@PathVariable Integer id, @Valid @RequestBody EmployeeUpdateDto employee) {
        String msg = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(new ResponseDto(Boolean.TRUE, HttpStatus.OK.value(), msg));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseDto> updateEmployeeStatus(@PathVariable Integer id, @RequestParam Integer status) {
        String msg = employeeService.updateEmployeeStatus(id, status);
        return ResponseEntity.ok(new ResponseDto(Boolean.TRUE, HttpStatus.OK.value(), msg));
    }

    @PatchMapping("/{id}/link-user")
    public ResponseEntity<ResponseDto> linkUserToEmployee(@PathVariable Integer id, @RequestParam Integer userId) {
        String msg = employeeService.linkUserToEmployee(id, userId);
        return ResponseEntity.ok(new ResponseDto(Boolean.TRUE, HttpStatus.OK.value(), msg));
    }

    @PatchMapping("/{id}/unlink-user")
    public ResponseEntity<ResponseDto> unlinkUserFromEmployee(@PathVariable Integer id) {
        String msg = employeeService.unlinkUserFromEmployee(id);
        return ResponseEntity.ok(new ResponseDto(Boolean.TRUE, HttpStatus.OK.value(), msg));
    }



}
