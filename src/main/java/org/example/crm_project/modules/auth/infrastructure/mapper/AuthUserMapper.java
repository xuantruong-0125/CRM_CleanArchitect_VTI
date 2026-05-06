package org.example.crm_project.modules.auth.infrastructure.mapper;

import org.example.crm_project.modules.auth.domain.entity.AuthUser;
import org.example.crm_project.modules.system_managerment.infrastructure.persistence.projection.UserAuthProjection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthUserMapper {

    public static AuthUser toAuthUser(List<UserAuthProjection> rows) {

        if (rows == null || rows.isEmpty()) {
            return null;
        }

        UserAuthProjection first = rows.get(0);

        Set<String> roles = Set.of(first.getRoleName());

        Set<String> permissions = rows.stream()
                .flatMap(row -> {
                    Set<String> perms = new HashSet<>();

                    String menu = row.getMenuCode();

                    if (Boolean.TRUE.equals(row.getCanView())) {
                        perms.add(menu + "_VIEW");
                    }

                    if (Boolean.TRUE.equals(row.getCanCreate())) {
                        perms.add(menu + "_CREATE");
                    }

                    if (Boolean.TRUE.equals(row.getCanUpdate())) {
                        perms.add(menu + "_UPDATE");
                    }

                    if (Boolean.TRUE.equals(row.getCanDelete())) {
                        perms.add(menu + "_DELETE");
                    }

                    return perms.stream();
                })
                .collect(Collectors.toSet());

        boolean isActive = "ACTIVE".equals(first.getStatus());
        System.out.println("FULLNAME FROM DB = " + first.getFullname());
        return new AuthUser(
                first.getId(),
                first.getUsername(),
                first.getFullname(),
                first.getPassword(),
                isActive,
                roles,
                permissions
        );
    }
}