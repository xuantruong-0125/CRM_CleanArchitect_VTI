package org.example.crm_project.modules.system_managerment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.dto.request.*;
import org.example.crm_project.modules.system_managerment.application.dto.response.UserResponse;
import org.example.crm_project.modules.system_managerment.application.mapper.UserMapper;
import org.example.crm_project.modules.system_managerment.domain.entity.User;
import org.example.crm_project.modules.system_managerment.domain.repository.UserRepository;
import org.example.crm_project.modules.system_managerment.domain.constant.UserStatus;
import org.example.crm_project.modules.system_managerment.domain.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    // ===== CREATE =====
    @Transactional
    public UserResponse create(CreateUserRequest req) {

        User user = new User(
                req.getUsername(),
                req.getPassword(), // ⚠️ sau này hash
                req.getEmail(),
                req.getFullName(),
                req.getRoleId(),
                req.getOrganizationId()
        );

        return UserMapper.toResponse(
                repository.save(user)
        );
    }

    // ===== GET ALL =====
    public List<UserResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    // ===== GET BY ID =====
    public UserResponse getById(Long id) {
        return repository.findById(id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // ===== UPDATE =====
    @Transactional
    public UserResponse update(Long id, UpdateUserRequest req) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (req.getEmail() != null) user.changeEmail(req.getEmail());
        if (req.getFullName() != null) user.changeFullName(req.getFullName());

        if (req.getRoleId() != null) {
            user.changeRole(req.getRoleId());
        }

        if (req.getOrganizationId() != null) {
            user.changeOrganization(req.getOrganizationId());
        }

        if (req.getStatus() != null) {
            user.changeStatus(UserStatus.valueOf(req.getStatus()));
        }

        return UserMapper.toResponse(
                repository.save(user)
        );
    }

    // ===== CHANGE PASSWORD =====
    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest req) {

        User user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.changePassword(req.getOldPassword(), req.getNewPassword());
        repository.save(user);
    }

    // ===== DELETE (SOFT) =====
    @Transactional
    public void delete(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.delete(); // set deletedAt
        repository.save(user);
    }
}