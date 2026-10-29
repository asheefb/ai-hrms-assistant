package com.asheef.hrms.service;

import com.asheef.hrms.entity.StaffingCodeDetails;
import com.asheef.hrms.model.dto.UserActivateDto;

public interface UserService {
    void createUser(StaffingCodeDetails staffingCodeDetails);

    String activateUserLogin(Integer userId, UserActivateDto userActivateDto);
}
