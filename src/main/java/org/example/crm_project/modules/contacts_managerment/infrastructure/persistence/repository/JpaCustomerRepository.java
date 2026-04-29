package org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.repository;

import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
