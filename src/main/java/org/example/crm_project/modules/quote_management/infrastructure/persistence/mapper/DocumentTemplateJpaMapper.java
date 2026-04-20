package org.example.crm_project.modules.quote_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.quote_management.domain.constant.TemplateType;
import org.example.crm_project.modules.quote_management.domain.entity.DocumentTemplate;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.entity.DocumentTemplateEntity;

/**
 * JPA Mapper: chuyển đổi giữa DocumentTemplateEntity (infrastructure) và DocumentTemplate (domain).
 */
public class DocumentTemplateJpaMapper {

    public static DocumentTemplate toDomain(DocumentTemplateEntity e) {
        return new DocumentTemplate(
                e.getId(),
                e.getType() != null ? TemplateType.valueOf(e.getType()) : null,
                e.getName(),
                e.getContentHtml(),
                e.getIsActive(),
                e.getCreatedAt()
        );
    }
}
