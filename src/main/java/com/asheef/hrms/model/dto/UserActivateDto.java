package com.asheef.hrms.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserActivateDto {

    @NotBlank(message = "Username is required")
    @Size(max = 155, message = "Username must not exceed 155 characters")
    private String userLogin;

    @NotBlank(message = "User profile ref_module is required")
    @Size(max = 50, message = "User profile ref_module must not exceed 50 characters")
    private String profile;
}
