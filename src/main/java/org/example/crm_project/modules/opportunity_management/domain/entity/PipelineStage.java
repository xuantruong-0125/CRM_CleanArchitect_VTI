package org.example.crm_project.modules.opportunity_management.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Domain Entity – PipelineStage (giai đoạn trong pipeline).
 * Chứa business logic tính toán probability.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PipelineStage {
    private Integer id;
    private String stageName;
    private Float probability;
    private Integer maxDaysAllowed;
    private Integer sortOrder;

    // Tham chiếu Pipeline
    private Integer pipelineId;
    private String pipelineName;

    // =========== Business Logic ===========

    /**
     * Tính lại probability cho toàn bộ danh sách stages trong cùng pipeline.
     * Phân bổ đều 100% cho tất cả stages.
     */
    public static void recalculateProbabilities(List<PipelineStage> stages) {
        if (stages == null || stages.isEmpty()) return;
        float baseProb = 100.0f / stages.size();
        float newProb = Math.round(baseProb * 100.0f) / 100.0f;
        stages.forEach(s -> s.setProbability(newProb));
    }
}
