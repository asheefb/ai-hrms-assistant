package com.asheef.hrms.service.impl;

import com.asheef.hrms.auth.dto.RegisterRequest;
import com.asheef.hrms.auth.dto.UserResponse;
import com.asheef.hrms.constants.ChildModules;
import com.asheef.hrms.constants.Constant;
import com.asheef.hrms.constants.ControlModules;
import com.asheef.hrms.entity.CommonModules;
import com.asheef.hrms.entity.MobileCountryCodes;
import com.asheef.hrms.entity.User;
import com.asheef.hrms.repo.CommonModulesRepository;
import com.asheef.hrms.repo.MobileCountryCodesRepository;
import com.asheef.hrms.repo.UserRepository;
import com.asheef.hrms.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final CommonModulesRepository commonModulesRepository;
    private final PasswordEncoder passwordEncoder;
    private final MobileCountryCodesRepository mobileCountryCodesRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           CommonModulesRepository commonModulesRepository,
                           PasswordEncoder passwordEncoder, MobileCountryCodesRepository mobileCountryCodesRepository) {
        this.userRepository = userRepository;
        this.commonModulesRepository = commonModulesRepository;
        this.passwordEncoder = passwordEncoder;
        this.mobileCountryCodesRepository = mobileCountryCodesRepository;
    }

    @Override
    @Transactional
    public UserResponse register(RegisterRequest req) {

        String email = normalise(req.email());
        String fullName = req.resolvedFullName();

        String login = req.firstName().toLowerCase() + req.lastName().substring(0, 1).toLowerCase();
        String password = passwordEncoder.encode(req.password());

        if (userRepository.findByLogin(login).isPresent() || userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with the same login or email already exists");
        }

        CommonModules userType = commonModulesRepository
                .findChildByModuleCodes(ControlModules.USER_TYPE, ChildModules.REGISTERED_USER)
                .orElseThrow(() -> new IllegalArgumentException("User type module not found"));

        CommonModules userProfile = commonModulesRepository
                .findChildByModuleCodes(ControlModules.USER_PROFIL, ChildModules.WEB_MOBILE_USER)
                .orElseThrow(() -> new IllegalArgumentException("User profile module not found"));

        User createdBy = userRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("Created by user not found"));

        MobileCountryCodes mobileCountryCode = mobileCountryCodesRepository.findByCountryCode(req.countryCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid country code"));

        String phoneNumber = "";
        if (req.phoneNumber() != null && !req.phoneNumber().isEmpty()) {
            phoneNumber = req.phoneNumber().trim();
            if (phoneNumber.length() != mobileCountryCode.getNumberLength()) {
                throw new IllegalArgumentException("Invalid phone number length for the given country code");
            }
        } else
            phoneNumber = Constant.DEFAULT_PHONE;

        User savedUser = userRepository.save(User.create(login, password, fullName, req.resolvedFirstName(), req.resolvedLastName(), email, req.countryCode(), phoneNumber, userProfile, userType, createdBy, createdBy));
        savedUser.setReferenceId(savedUser.getId());
        userRepository.save(savedUser);

        return UserResponse.from(savedUser);
    }

    private static String normalise(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
