package org.example.crm_project.modules.system_managerment.infrastructure.persistence.projection;

public interface UserAuthProjection {

    Long getId();
    String getUsername();
    String getFullname();
    String getPassword();
    String getStatus();

    String getRoleName();
    String getScope();
    String getMenuCode();

    Boolean getCanView();
    Boolean getCanCreate();
    Boolean getCanUpdate();
    Boolean getCanDelete();
}