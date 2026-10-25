package com.asheef.hrms.service.impl;

import com.asheef.hrms.constants.ChildModules;
import com.asheef.hrms.constants.Constant;
import com.asheef.hrms.entity.StaffingCodeDetails;
import com.asheef.hrms.entity.User;
import com.asheef.hrms.repo.CommonModulesRepository;
import com.asheef.hrms.repo.UserRepository;
import com.asheef.hrms.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
            user.setCountryCode(staffingCode);
            user.setMobile(staffingCode);





        } else
            throw new RuntimeException("User Already created for this Staffing Code");

    }
}
