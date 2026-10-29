package com.asheef.hrms.controller;

import com.asheef.hrms.model.dto.UserActivateDto;
import com.asheef.hrms.service.UserService;
import com.asheef.hrms.utils.ResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/activate-login/{userId}")
    public ResponseEntity<ResponseDto> activateUserLogin(@PathVariable Integer userId, @Valid @RequestBody UserActivateDto userActivateDto) {

        String msg = userService.activateUserLogin(userId, userActivateDto);
        return ResponseEntity.
                ok(new ResponseDto(Boolean.TRUE, HttpStatus.OK.value(), msg));
    }

}
