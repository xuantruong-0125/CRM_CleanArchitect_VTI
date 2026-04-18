package org.example.crm_project.modules.system_managerment.domain.entity;

import org.example.crm_project.modules.system_managerment.domain.exception.InvalidPermissionException;

public class RoleMenuPermission {

    private Long roleId;
    private Long menuId;

    private boolean canView;
    private boolean canCreate;
    private boolean canUpdate;
    private boolean canDelete;

    // ===== CONSTRUCTOR =====
    public RoleMenuPermission(Long roleId, Long menuId) {
        if (roleId == null || menuId == null) {
            throw new IllegalArgumentException("RoleId and MenuId must not be null");
        }
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public RoleMenuPermission(
            Long roleId,
            Long menuId,
            boolean canView,
            boolean canCreate,
            boolean canUpdate,
            boolean canDelete
    ) {
        this(roleId, menuId);
        this.canView = canView;
        this.canCreate = canCreate;
        this.canUpdate = canUpdate;
        this.canDelete = canDelete;

        validate();
    }

    // ===== GETTER =====
    public Long getRoleId() { return roleId; }
    public Long getMenuId() { return menuId; }
    public boolean isCanView() { return canView; }
    public boolean isCanCreate() { return canCreate; }
    public boolean isCanUpdate() { return canUpdate; }
    public boolean isCanDelete() { return canDelete; }

    // ===== BUSINESS LOGIC =====

    public void allowView() {
        this.canView = true;
    }

    public void revokeView() {
        this.canView = false;

        // 🔥 nếu bỏ view thì phải bỏ hết quyền khác
        this.canCreate = false;
        this.canUpdate = false;
        this.canDelete = false;
    }

    public void allowCreate() {
        requireView();
        this.canCreate = true;
    }

    public void allowUpdate() {
        requireView();
        this.canUpdate = true;
    }

    public void allowDelete() {
        requireView();
        this.canDelete = true;
    }

    public void revokeCreate() {
        this.canCreate = false;
    }

    public void revokeUpdate() {
        this.canUpdate = false;
    }

    public void revokeDelete() {
        this.canDelete = false;
    }

    // ===== VALIDATION =====

    private void requireView() {
        if (!canView) {
            throw new InvalidPermissionException("Must have VIEW permission first");
        }
    }

    private void validate() {
        if ((canCreate || canUpdate || canDelete) && !canView) {
            throw new InvalidPermissionException(
                    "CREATE/UPDATE/DELETE require VIEW permission"
            );
        }
    }
}