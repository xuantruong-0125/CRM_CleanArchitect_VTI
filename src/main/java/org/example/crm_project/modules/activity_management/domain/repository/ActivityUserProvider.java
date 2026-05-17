package org.example.crm_project.modules.activity_management.domain.repository;

public interface ActivityUserProvider {
    // Duy chỉ cần tên nhân viên dựa vào ID
    String getUserFullNameById(Long userId);
}
