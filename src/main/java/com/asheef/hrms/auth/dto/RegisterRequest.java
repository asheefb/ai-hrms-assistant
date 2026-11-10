package com.asheef.hrms.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Size(max = 150) String firstName,
        @Size(max = 150) String lastName,
        @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Size(min = 8, max = 72) String password,
        String countryCode,
        String phoneNumber,
        String fullName
) {
    public String resolvedFirstName() {
        if (firstName != null && !firstName.isBlank()) {
            return firstName.trim();
        }
        String name = fullName != null ? fullName.trim() : "";
        if (name.isEmpty()) {
            return "";
        }
        int spaceIndex = name.indexOf(' ');
        return spaceIndex >= 0 ? name.substring(0, spaceIndex).trim() : name;
    }

    public String resolvedLastName() {
        if (lastName != null && !lastName.isBlank()) {
            return lastName.trim();
        }
        String name = fullName != null ? fullName.trim() : "";
        if (name.isEmpty()) {
            return "";
        }
        int spaceIndex = name.indexOf(' ');
        if (spaceIndex < 0) {
            return "";
        }
        return name.substring(spaceIndex + 1).trim();
    }

    public String resolvedFullName() {
        String name = fullName != null ? fullName.trim() : "";
        if (!name.isEmpty()) {
            return name;
        }
        String first = resolvedFirstName();
        String last = resolvedLastName();
        if (first.isEmpty() && last.isEmpty()) {
            return "";
        }
        if (first.isEmpty()) {
            return last;
        }
        if (last.isEmpty()) {
            return first;
        }
        return first + " " + last;
    }
}
