package org.example.crm_project.modules.activity_management.infrastructure.adapter;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.example.crm_project.modules.activity_management.domain.repository.ActivityUserProvider;

import org.example.crm_project.modules.system_managerment.application.service.UserService; 

@Component
@RequiredArgsConstructor
public class ActivityUserProviderImpl implements ActivityUserProvider {
    
    private final UserService userService; 

    @Override
    @Cacheable(value = "activity_user_names", key = "#userId")
    public String getUserFullNameById(Long userId) {
        if (userId == null) return "Chưa phân công";

        try {
            // Gọi sang module User để lấy thông tin
            // Tùy vào việc UserService trả về cái gì, map ra cái Tên 
            var user = userService.getById(userId); 
            return user.getFullName(); 
            
        } catch (Exception e) {
          
            return "Người dùng ẩn danh"; 
        }
    }
}