package org.example.crm_project.modules.opportunity_management.application.service;

import org.example.crm_project.modules.opportunity_management.domain.entity.User;
import org.example.crm_project.modules.opportunity_management.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application Service – Đọc dữ liệu User (module ngoài).
 */
@Service("opportunityUserService")
@RequiredArgsConstructor
public class UserAppService {

    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
