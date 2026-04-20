package org.example.crm_project.modules.leads.domain.repository;

import org.example.crm_project.modules.leads.domain.entity.LeadReferenceOption;

import java.util.List;

public interface LeadReferenceCatalogRepository {

    List<LeadReferenceOption> findLeadStatuses();

    List<LeadReferenceOption> findLeadSources();

    List<LeadReferenceOption> findProvinces();

    List<LeadReferenceOption> findOrganizations();

    List<LeadReferenceOption> findProducts();

    List<LeadReferenceOption> searchAssignees(String q,
                                              Long organizationId,
                                              Long roleId,
                                              String status,
                                              int page,
                                              int size,
                                              String sortBy,
                                              String sortDir);

    long countAssignees(String q, Long organizationId, Long roleId, String status);

    List<LeadReferenceOption> searchProducts(String q,
                                             String type,
                                             Long categoryId,
                                             Boolean isActive,
                                             int page,
                                             int size,
                                             String sortBy,
                                             String sortDir);

    long countProducts(String q, String type, Long categoryId, Boolean isActive);

    List<LeadReferenceOption> searchProvinces(String q,
                                              String code,
                                              int page,
                                              int size,
                                              String sortBy,
                                              String sortDir);

    long countProvinces(String q, String code);
}