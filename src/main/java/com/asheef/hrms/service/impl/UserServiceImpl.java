package com.asheef.hrms.service.impl;

import com.asheef.hrms.constants.ChildModules;
import com.asheef.hrms.constants.Constant;
import com.asheef.hrms.constants.ControlModules;
import com.asheef.hrms.entity.CommonModules;
import com.asheef.hrms.entity.StaffingCodeDetails;
import com.asheef.hrms.entity.User;
import com.asheef.hrms.model.dto.UserActivateDto;
import com.asheef.hrms.repo.CommonModulesRepository;
import com.asheef.hrms.repo.UserRepository;
import com.asheef.hrms.service.UserService;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final CommonModulesRepository commonModulesRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CommonModulesRepository commonModulesRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.commonModulesRepository = commonModulesRepository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createUser(StaffingCodeDetails staffingCodeDetails) {
        Optional<User> isUserExist = userRepository.findByType_ModuleCodeAndReferenceId(ChildModules.USER_EMPLOYEE, staffingCodeDetails.getId());

        if (isUserExist.isEmpty()) {
            String staffingCode = staffingCodeDetails.getStaffingCode();

            User user = new User();
            user.setLogin(staffingCode);
            user.setPassword(passwordEncoder.encode(Constant.DEFAULT_PASSWORD));
            user.setName(staffingCode);
            user.setFirstname(staffingCode);
            user.setLastname(staffingCode);
            user.setCountryCode("00");
            user.setMobile(staffingCode);

            //profil
            CommonModules disabledUserModule = commonModulesRepository.findChildByModuleCodes(ControlModules.USER_PROFIL, ChildModules.DISABLED_USER)
                    .orElseThrow(() -> new RuntimeException("Disabled User module not found"));

            user.setProfil(disabledUserModule);

            CommonModules userTypeModule = commonModulesRepository.findChildByModuleCodes(ControlModules.USER_TYPE, ChildModules.USER_EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Staffing Status module not found"));
            user.setType(userTypeModule);
            User createdBy = userRepository.findById(1)
                    .orElseThrow(() -> new NoSuchElementException(Constant.NOT_FOUND));

            user.setCreatedBy(createdBy);
            user.setModifiedBy(createdBy);

            User savedUser = userRepository.save(user);
            Integer referenceId = savedUser.getId();

            savedUser.setReferenceId(referenceId);
            userRepository.save(savedUser);

        } else
            throw new RuntimeException("User Already created for this Staffing Code");
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String activateUserLogin(Integer userId, UserActivateDto userActivateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(Constant.INVALID_USER_ID + userId));

        String login = userActivateDto.getUserLogin().trim();
        Optional<User> userWithLogin = userRepository.findByLogin(login);
        if (userWithLogin.isPresent() && !userWithLogin.get().getId().equals(userId)) {
            throw new IllegalArgumentException("Username is already in use: " + login);
        }

        CommonModules profile = commonModulesRepository.findActiveChildByRefModuleAndParentModuleCode(userActivateDto.getProfile().trim(), ControlModules.USER_PROFIL)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user profile ref_module: " + userActivateDto.getProfile()));

        user.setLogin(login);
        user.setProfil(profile);
        userRepository.save(user);

        return Constant.SUCCESS;
    }
}
