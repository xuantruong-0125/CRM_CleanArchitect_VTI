package org.example.crm_project.modules.contracts.infrastructure.persistence.mapper;

import org.example.crm_project.modules.contracts.domain.entity.Contract;
import org.example.crm_project.modules.contracts.infrastructure.persistence.entity.ContractJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContractPersistenceMapper {

    Contract toDomain(ContractJpaEntity entity);

    ContractJpaEntity toJpaEntity(Contract domain);

    void updateJpaEntity(@MappingTarget ContractJpaEntity target, Contract source);
}
