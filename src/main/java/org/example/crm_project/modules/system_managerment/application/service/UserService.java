package org.example.crm_project.modules.system_managerment.application.service;

import org.example.crm_project.modules.system_managerment.application.dto.response.UserResponse;
import java.util.List;

public interface UserService {
    List<UserResponse> findAll();
}
