package org.example.crm_project.modules.opportunity_management.domain.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.Opportunity;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Domain Repository Interface (Port).
 * Lớp domain định nghĩa HỢP ĐỒNG – Infrastructure phải thực thi.
 * Tuân theo Dependency Inversion: domain không biết gì về JPA hay DB.
 */
public interface OpportunityRepository {

    List<Opportunity> findAll();

    Optional<Opportunity> findById(Integer id);

    Opportunity save(Opportunity opportunity);

    void deleteById(Integer id);

    /**
     * Lọc động với phân trang và sắp xếp.
     */
    Page<Opportunity> filter(String keyword, Integer customerId, Integer assignedUserId,
                             Integer pipelineId, Integer stageId, String healthStatus,
                             LocalDate dateFrom, LocalDate dateTo,
                             String sortField, String sortDir, int page, int size);
}
