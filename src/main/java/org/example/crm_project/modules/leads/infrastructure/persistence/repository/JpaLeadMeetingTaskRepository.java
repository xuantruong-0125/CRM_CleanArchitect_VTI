package org.example.crm_project.modules.leads.infrastructure.persistence.repository;

import org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadMeetingTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaLeadMeetingTaskRepository extends JpaRepository<LeadMeetingTaskEntity, Long> {

    Optional<LeadMeetingTaskEntity> findByIdAndRelatedToTypeAndRelatedToIdAndDeletedAtIsNull(Long id, String relatedToType, Long relatedToId);
}