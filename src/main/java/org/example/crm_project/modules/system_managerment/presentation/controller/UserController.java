package org.example.crm_project.modules.system_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.system_managerment.application.service.UserService;
import org.example.crm_project.modules.system_managerment.presentation.dto.request.*;
import org.example.crm_project.modules.system_managerment.presentation.dto.response.UserResponseDto;
import org.example.crm_project.modules.system_managerment.presentation.mapper.UserPresentationMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    // ===== CREATE =====
    @PostMapping
    public UserResponseDto create(@RequestBody CreateUserRequestDto dto) {
        return UserPresentationMapper.toDto(
                service.create(
                        UserPresentationMapper.toCreateRequest(dto)
                )
        );
    }

    // ===== GET ALL =====
    @GetMapping
    public List<UserResponseDto> getAll() {
        return service.getAll()
                .stream()
                .map(UserPresentationMapper::toDto)
                .toList();
    }

    // ===== GET BY ID =====
    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return UserPresentationMapper.toDto(
                service.getById(id)
        );
    }

    // ===== UPDATE =====
    @PutMapping("/{id}")
    public UserResponseDto update(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestDto dto
    ) {
        return UserPresentationMapper.toDto(
                service.update(id,
                        UserPresentationMapper.toUpdateRequest(dto))
        );
    }

    // ===== CHANGE PASSWORD =====
    @PutMapping("/{id}/password")
    public void changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequestDto dto
    ) {
        service.changePassword(id,
                UserPresentationMapper.toChangePassword(dto));
    }

    // ===== DELETE =====
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}