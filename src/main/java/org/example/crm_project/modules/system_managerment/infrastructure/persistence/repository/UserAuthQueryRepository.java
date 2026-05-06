package org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.UserEntity;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.projection.UserAuthProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserAuthQueryRepository extends Repository<UserEntity, Long> {

    @Query(value = """
        SELECT
            u.id as id,
            u.username as username,
            u.full_name as fullname,
            u.password as password,
            u.status as status,
            r.id as roleId,
            r.name as roleName,
            m.code as menuCode,
            rmp.can_view,
            rmp.can_create,
            rmp.can_update,
            rmp.can_delete
        FROM users u
        JOIN roles r ON u.role_id = r.id
        LEFT JOIN role_menu_permissions rmp ON r.id = rmp.role_id
        LEFT JOIN menus m ON rmp.menu_id = m.id
        WHERE u.username = :username
    """, nativeQuery = true)
    List<UserAuthProjection> findUserAuthData(String username);
}