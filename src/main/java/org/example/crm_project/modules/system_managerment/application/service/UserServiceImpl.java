package org.example.crm_project.modules.system_managerment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.dto.response.UserResponse;
import org.example.crm_project.modules.system_managerment.application.mapper.UserMapper;
import org.example.crm_project.modules.system_managerment.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }
}
