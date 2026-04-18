package org.example.crm_project.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.application.dto.request.CreateOrganizationRequest;
import org.example.crm_project.application.dto.request.UpdateOrganizationRequest;
import org.example.crm_project.application.dto.response.OrganizationResponse;
import org.example.crm_project.application.mapper.OrganizationMapper;
import org.example.crm_project.domain.constant.OrganizationType;
import org.example.crm_project.domain.entity.Organization;
import org.example.crm_project.domain.exception.OrganizationNotFoundException;
import org.example.crm_project.domain.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;

    // ===== CREATE =====
    public OrganizationResponse create(CreateOrganizationRequest req) {

        // validate parent tồn tại
        if (req.getParentId() != null && !repository.existsById(req.getParentId())) {
            throw new RuntimeException("Parent organization not found");
        }

        Organization org = OrganizationMapper.toEntity(req);

        return OrganizationMapper.toResponse(repository.save(org));
    }

    // ===== UPDATE =====
    public OrganizationResponse update(Long id, UpdateOrganizationRequest req) {

        Organization org = repository.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException(id));

        // validate parent
        if (req.getParentId() != null && !repository.existsById(req.getParentId())) {
            throw new RuntimeException("Parent organization not found");
        }

        // 👉 dùng domain behavior (QUAN TRỌNG)
        if (req.getName() != null) {
            org.changeName(req.getName());
        }

        if (req.getParentId() != null) {
            org.changeParent(req.getParentId());
        }

        if (req.getType() != null) {
            org.changeType(OrganizationType.valueOf(req.getType()));
        }

        return OrganizationMapper.toResponse(repository.save(org));
    }

    // ===== GET BY ID =====
    public OrganizationResponse getById(Long id) {
        return repository.findById(id)
                .map(OrganizationMapper::toResponse)
                .orElseThrow(() -> new OrganizationNotFoundException(id));
    }

    // ===== GET ALL =====
    public List<OrganizationResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(OrganizationMapper::toResponse)
                .toList();
    }

    // ===== DELETE =====
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new OrganizationNotFoundException(id);
        }
        repository.deleteById(id);
    }
}