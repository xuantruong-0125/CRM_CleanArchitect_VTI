package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.opportunity_management.domain.entity.Opportunity;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.LossReasonJpaEntity;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.OpportunityJpaEntity;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.PipelineJpaEntity;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.PipelineStageJpaEntity;
import org.springframework.stereotype.Component;


/**
 * Infrastructure Mapper: chuyển đổi JPA Entity ↔ Domain Model.
 * Giữ cho Domain Model sạch, không chứa annotation JPA.
 */
@Component
public class OpportunityEntityMapper {

    /**
     * JPA Entity → Domain Model (kèm snapshot data cho display).
     */
    public Opportunity toDomain(OpportunityJpaEntity entity) {
        if (entity == null) return null;

        return Opportunity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .customerId(entity.getCustomerId())
                .assignedUserId(entity.getAssignedUserId())
                .pipelineId(entity.getPipeline() != null ? entity.getPipeline().getId() : null)
                .stageId(entity.getStage() != null ? entity.getStage().getId() : null)
                .lossReasonId(entity.getLossReason() != null ? entity.getLossReason().getId() : null)
                .totalAmount(entity.getTotalAmount())
                .depositAmount(entity.getDepositAmount())
                .remainingAmount(entity.getRemainingAmount())
                .currencyCode(entity.getCurrencyCode())
                .exchangeRate(entity.getExchangeRate())
                .healthStatus(entity.getHealthStatus())
                .expectedCloseDate(entity.getExpectedCloseDate())
                // Snapshot data
                .customerName(entity.getCustomer() != null ? entity.getCustomer().getName() : null)
                .assignedUserFullName(entity.getAssignedUser() != null ? entity.getAssignedUser().getFullName() : null)
                .pipelineName(entity.getPipeline() != null ? entity.getPipeline().getName() : null)
                .stageName(entity.getStage() != null ? entity.getStage().getStageName() : null)
                .lossReasonName(entity.getLossReason() != null ? entity.getLossReason().getName() : null)
                .build();
    }

    /**
     * Domain Model → JPA Entity (để lưu vào DB).
     */
    public OpportunityJpaEntity toEntity(Opportunity domain) {
        if (domain == null) return null;

        OpportunityJpaEntity entity = new OpportunityJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setCustomerId(domain.getCustomerId());
        entity.setAssignedUserId(domain.getAssignedUserId());
        entity.setTotalAmount(domain.getTotalAmount());
        entity.setDepositAmount(domain.getDepositAmount());
        entity.setRemainingAmount(domain.getRemainingAmount());
        entity.setCurrencyCode(domain.getCurrencyCode());
        entity.setExchangeRate(domain.getExchangeRate());
        entity.setHealthStatus(domain.getHealthStatus());
        entity.setExpectedCloseDate(domain.getExpectedCloseDate());

        // Tạo proxy object chỉ với ID để JPA nhận diện FK
        if (domain.getPipelineId() != null) {
            PipelineJpaEntity pipeline = new PipelineJpaEntity();
            pipeline.setId(domain.getPipelineId());
            entity.setPipeline(pipeline);
        }
        if (domain.getStageId() != null) {
            PipelineStageJpaEntity stage = new PipelineStageJpaEntity();
            stage.setId(domain.getStageId());
            entity.setStage(stage);
        }
        if (domain.getLossReasonId() != null) {
            LossReasonJpaEntity lr = new LossReasonJpaEntity();
            lr.setId(domain.getLossReasonId());
            entity.setLossReason(lr);
        }

        return entity;
    }
}
