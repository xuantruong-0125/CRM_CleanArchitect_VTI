package org.example.crm_project.modules.system_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.system_managerment.domain.entity.User;
import org.example.crm_project.modules.system_managerment.domain.constant.UserStatus;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.UserEntity;

public class UserJpaMapper {

    public static UserEntity toEntity(User u) {
        UserEntity e = new UserEntity();

        e.setId(u.getId());
        e.setUsername(u.getUsername());
        e.setPassword(u.getPassword());
        e.setEmail(u.getEmail());
        e.setFullName(u.getFullName());
        e.setRoleId(u.getRoleId());
        e.setOrganizationId(u.getOrganizationId());
        e.setStatus(u.getStatus().name());
        e.setLastLogin(u.getLastLogin());
        e.setCreatedAt(u.getCreatedAt());
        e.setUpdatedAt(u.getUpdatedAt());
        e.setDeletedAt(u.getDeletedAt());

        return e;
    }

    public static User toDomain(UserEntity e) {
        User user = new User(
                e.getUsername(),
                e.getPassword(),
                e.getEmail(),
                e.getFullName(),
                e.getRoleId(),
                e.getOrganizationId()
        );

        // set id + trạng thái (cần thêm constructor overload nếu muốn clean hơn)
        try {
            var idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(user, e.getId());

            user.changeStatus(UserStatus.valueOf(e.getStatus()));

        } catch (Exception ex) {
            throw new RuntimeException("Mapping error");
        }

        return user;
    }
}