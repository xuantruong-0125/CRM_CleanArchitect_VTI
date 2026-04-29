package org.example.crm_project.modules.leads.application.service;

import org.example.crm_project.modules.leads.application.dto.response.LeadReferenceCatalogResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadPageResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadReferenceOptionResponse;

public interface LeadReferenceService {

    LeadReferenceCatalogResponse getReferenceCatalog();

    LeadPageResponse<LeadReferenceOptionResponse> searchAssignees(String q,
                                                                  Long organizationId,
                                                                  Long roleId,
                                                                  String status,
                                                                  Integer page,
                                                                  Integer size,
                                                                  String sortBy,
                                                                  String sortDir);

    LeadPageResponse<LeadReferenceOptionResponse> searchProducts(String q,
                                                                 String type,
                                                                 Long categoryId,
                                                                 Boolean isActive,
                                                                 Integer page,
                                                                 Integer size,
                                                                 String sortBy,
                                                                 String sortDir);

    LeadPageResponse<LeadReferenceOptionResponse> searchProvinces(String q,
                                                                  String code,
                                                                  Integer page,
                                                                  Integer size,
                                                                  String sortBy,
                                                                  String sortDir);

    LeadPageResponse<LeadReferenceOptionResponse> searchOrganizations(String q,
                                                                      Integer page,
                                                                      Integer size,
                                                                      String sortBy,
                                                                      String sortDir);
}