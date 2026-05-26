package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper;


import org.example.crm_project.modules.opportunity_management.domain.entity.User;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Infrastructure Mapper: User JPA Entity ↔ Domain Model.
 */
@Component
public class UserEntityMapper {

    public User toDomain(UserJpaEntity entity) {
        if (entity == null) return null;
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .build();
    }
}
