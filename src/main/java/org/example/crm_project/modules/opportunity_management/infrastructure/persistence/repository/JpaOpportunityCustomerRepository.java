package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA interface – Customer.
 */
public interface JpaOpportunityCustomerRepository extends JpaRepository<CustomerJpaEntity, Integer> {
}
