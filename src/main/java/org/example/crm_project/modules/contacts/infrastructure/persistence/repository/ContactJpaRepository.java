package org.example.crm_project.modules.contacts.infrastructure.persistence.repository;

import org.example.crm_project.modules.contacts.infrastructure.persistence.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactJpaRepository extends JpaRepository<ContactEntity, Long> {
}
