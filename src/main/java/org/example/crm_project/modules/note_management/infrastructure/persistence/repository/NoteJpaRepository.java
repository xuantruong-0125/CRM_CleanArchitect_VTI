package org.example.crm_project.modules.note_management.infrastructure.persistence.repository;

import java.util.List;

import org.example.crm_project.modules.note_management.infrastructure.persistence.entity.NoteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteJpaRepository extends JpaRepository<NoteJpaEntity, Long> {
    // Tìm tất cả note theo loại và id, sắp xếp mới nhất lên đầu
List<NoteJpaEntity> findAllByNotableTypeAndNotableIdOrderByCreatedDateDesc(String notableType, Long notableId);
}
