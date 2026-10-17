package com.asheef.hrms.controller;

import com.asheef.hrms.entity.Employee;
import com.asheef.hrms.model.dto.EmployeeRequestDto;
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
    public ResponseEntity<ResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto employee) {

        String msg = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(Boolean.TRUE, HttpStatus.CREATED.value(), msg));
    }


}
