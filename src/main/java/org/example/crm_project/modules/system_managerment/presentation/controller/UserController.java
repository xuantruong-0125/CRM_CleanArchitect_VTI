package org.example.crm_project.modules.system_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.example.crm_project.modules.system_managerment.application.dto.response.PaginationResponse;
import org.example.crm_project.modules.system_managerment.application.service.UserService;
import org.example.crm_project.modules.system_managerment.presentation.dto.request.*;
import org.example.crm_project.modules.system_managerment.presentation.dto.response.UserResponseDto;
import org.example.crm_project.modules.system_managerment.presentation.mapper.UserPresentationMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

//    // ===== GET ALL =====
//    @GetMapping
//    public List<UserResponseDto> getAll() {
//        return service.getAll()
//                .stream()
//                .map(UserPresentationMapper::toDto)
//                .toList();
//    }

    @GetMapping
    public PaginationResponse<UserResponseDto> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ) {
        var result = service.getUsers(page, size);

        return PaginationResponse.<UserResponseDto>builder()
                .content(result.getContent()
                        .stream()
                        .map(UserPresentationMapper::toDto)
                        .toList())
                .page(result.getPage())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    // ===== SEARCH (FILTER) =====
    @GetMapping("/search")
    public PaginationResponse<UserResponseDto> searchUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) Long organizationId
    ) {
        var result = service.searchUsers(page, size, roleId, organizationId);

        return PaginationResponse.<UserResponseDto>builder()
                .content(result.getContent()
                        .stream()
                        .map(UserPresentationMapper::toDto)
                        .toList())
                .page(result.getPage())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
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

    @GetMapping("/lookup")
    public ResponseEntity<List<UserResponseDto>> getLookupUsers() {
        
        List<UserResponseDto> responses = service.getUsersByMyOrganization()
                .stream()
                .map(UserPresentationMapper::toDto)
                .toList();
                
        return ResponseEntity.ok(responses);
    }
}
