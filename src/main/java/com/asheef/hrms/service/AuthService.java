package com.asheef.hrms.service;

import com.asheef.hrms.auth.dto.RegisterRequest;
import com.asheef.hrms.auth.dto.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest req);
}
