package org.example.crm_project.modules.activity_management.infrastructure.adapter;

import org.example.crm_project.modules.activity_management.domain.repository.ActivityUserProvider;
import org.springframework.stereotype.Component;

@Component
public class ActivityUserProviderImpl implements ActivityUserProvider {

    @Override
    public String getUserNameById(Long userId) {
        // TẠM THỜI: Trả về tên giả hoặc gọi tạm database nếu Duy biết bảng User
        // Sau này xong, chỉ cần sửa đúng 1 dòng ở đây thôi.
        return "Nhân viên ID " + userId;
    }
}