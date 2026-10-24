package com.asheef.hrms.service.impl;

import com.asheef.hrms.constants.ChildModules;
import com.asheef.hrms.entity.StaffingCodeDetails;
import com.asheef.hrms.entity.User;
import com.asheef.hrms.repo.UserRepository;
import com.asheef.hrms.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

//    private final

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(StaffingCodeDetails staffingCodeDetails) {
        Optional<User> isUserExist = userRepository.findByType_ModuleCodeAndReferenceId(ChildModules.USER_EMPLOYEE, staffingCodeDetails.getId());

        if (isUserExist.isEmpty()) {
            User user = new User();
            user.setLogin(staffingCodeDetails.getStaffingCode());


        } else
            throw new RuntimeException("User Already created for this Staffing Code");

    }
}
