package org.example.crm_project.modules.system_managerment.domain.exception;

public class RoleMenuPermissionNotFoundException extends RuntimeException {
    public RoleMenuPermissionNotFoundException(Long roleId, Long menuId) {
        super("Permission not found for roleId=" + roleId + ", menuId=" + menuId);
    }
}