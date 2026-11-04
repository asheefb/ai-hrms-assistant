package com.asheef.hrms.service;

import com.asheef.hrms.entity.StaffingCodeDetails;
import com.asheef.hrms.entity.User;
import com.asheef.hrms.model.dto.UserActivateDto;

public interface UserService {
    void createUser(StaffingCodeDetails staffingCodeDetails);

    void activateUserForEmployee(User user, StaffingCodeDetails staffingCodeDetails);

    void resetUserToStaffingCode(User user, StaffingCodeDetails staffingCodeDetails);

    String activateUserLogin(Integer userId, UserActivateDto userActivateDto);
}
