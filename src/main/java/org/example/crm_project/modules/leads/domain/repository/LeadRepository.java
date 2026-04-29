package org.example.crm_project.modules.leads.domain.repository;

import org.example.crm_project.modules.leads.domain.entity.Lead;
import org.example.crm_project.modules.leads.domain.entity.LeadConversionResult;
import org.example.crm_project.modules.leads.domain.entity.LeadPageResult;

import java.util.List;
import java.util.Optional;

public interface LeadRepository {

    Lead save(Lead lead);

    Optional<Lead> findById(Long id);

    List<Lead> findAll();

        LeadPageResult<Lead> findAll(int page, int size, String sortBy, String sortDir);

    List<Lead> search(Integer provinceId, Long organizationId, String phone, String email, Long statusId, Long sourceId);

        LeadPageResult<Lead> search(
            Integer provinceId,
            Long organizationId,
            String phone,
            String email,
            Long statusId,
            Long sourceId,
            int page,
            int size,
            String sortBy,
            String sortDir
        );

    LeadConversionResult convert(Long leadId, Long userId);

    void deleteById(Long id);

    boolean existsById(Long id);
}