package org.example.crm_project.modules.leads.infrastructure.persistence.repository;

import org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadProductInterestEntity;
import org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadProductInterestId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaLeadProductInterestRepository extends JpaRepository<LeadProductInterestEntity, LeadProductInterestId> {

    List<LeadProductInterestEntity> findAllByLeadId(Long leadId);

    void deleteAllByLeadId(Long leadId);
}