package com.asheef.hrms.auth.dto;

import com.asheef.hrms.entity.User;

public record UserResponse(Integer id, String login, String email, String fullName, String profil) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getName(),
//                user.getRole().name(),
                user.getProfil() != null ? user.getProfil().getRefModule() : null
        );
    }
}
