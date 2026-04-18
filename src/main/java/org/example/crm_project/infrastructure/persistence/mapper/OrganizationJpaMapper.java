package org.example.crm_project.infrastructure.persistence.mapper;

import org.example.crm_project.domain.constant.OrganizationType;
import org.example.crm_project.domain.entity.Organization;
import org.example.crm_project.infrastructure.persistence.entity.OrganizationEntity;

public class OrganizationJpaMapper {

    public static OrganizationEntity toEntity(Organization org) {
        OrganizationEntity e = new OrganizationEntity();
        e.setId(org.getId());
        e.setName(org.getName());
        e.setParentId(org.getParentId());
        e.setType(org.getType().name());
        return e;
    }

    public static Organization toDomain(OrganizationEntity e) {
        return new Organization(
                e.getId(),
                e.getName(),
                e.getParentId(),
                OrganizationType.valueOf(e.getType())
        );
    }
}