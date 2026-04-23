package org.example.crm_project.modules.system_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.*;
//import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    // 🔥 chỉ lấy user chưa bị xóa
    @Query("SELECT u FROM UserEntity u WHERE u.deletedAt IS NULL")
    List<UserEntity> findAllActive();

    // 🔥 pagination (quan trọng khi >1000)
    @Query("SELECT u FROM UserEntity u WHERE u.deletedAt IS NULL")
    Page<UserEntity> findAllActive(Pageable pageable);
}