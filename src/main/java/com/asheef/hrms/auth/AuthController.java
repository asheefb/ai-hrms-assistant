package com.asheef.hrms.auth;

import com.asheef.hrms.auth.dto.RegisterRequest;
import com.asheef.hrms.auth.dto.UserResponse;
import com.asheef.hrms.common.api.ApiResponse;
import com.asheef.hrms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ApiResponse.ok(authService.register(req));
    }


}
