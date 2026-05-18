package org.example.crm_project.modules.note_management.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.example.crm_project.modules.note_management.Domain.entity.Note;
import org.example.crm_project.modules.note_management.Domain.repository.NoteRepository;
import org.example.crm_project.modules.note_management.infrastructure.persistence.entity.NoteJpaEntity;
import org.example.crm_project.modules.note_management.infrastructure.persistence.repository.NoteJpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository // BẮT BUỘC CÓ: Để Spring tạo Bean và Inject vào Service
@RequiredArgsConstructor
public class NoteRepositoryAdapter implements NoteRepository {

    private final NoteJpaRepository jpaRepository; 

    // 1. Thực thi hàm SAVE
    @Override
    public Note save(Note note) {
        // A. Chuyển từ Domain -> Entity
        NoteJpaEntity entity = new NoteJpaEntity();
        entity.setId(note.getId()); // Sẽ null nếu là tạo mới, có ID nếu là update
        entity.setContent(note.getContent());
        entity.setNotableType(note.getNotableType());
        entity.setNotableId(note.getNotableId());
        entity.setIsPrivate(note.getIsPrivate());
        entity.setCreatedBy(note.getCreatedBy());

        // B. Lưu xuống DB qua Spring Data JPA
        NoteJpaEntity savedEntity = jpaRepository.save(entity);

        // C. Chuyển Entity ngược lại thành Domain và trả về
        return toDomain(savedEntity);
    }

    // 2. Thực thi hàm DELETE
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    // 3. Thực thi hàm TÌM KIẾM
    @Override
    public List<Note> findByNotableTypeAndNotableId(String notableType, Long notableId) {
        // Sử dụng cái hàm JPA có Index và sắp xếp ngày giảm dần (Mới nhất lên đầu)
        List<NoteJpaEntity> entities = jpaRepository.findAllByNotableTypeAndNotableIdOrderByCreatedDateDesc(notableType, notableId);

        // Map nguyên cái danh sách Entity sang Domain
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    // ==========================================
    // Hàm Helper nội bộ: Dùng để dịch từ Entity sang Domain
    // Duy truyền theo đúng thứ tự Constructor đã viết ở class Note nhé!
    // ==========================================
    private Note toDomain(NoteJpaEntity entity) {
        return new Note(
            entity.getId(),
            entity.getContent(),
            entity.getNotableType(),
            entity.getNotableId(),
            entity.getIsPrivate(),
            entity.getCreatedBy()
        );
    }
}