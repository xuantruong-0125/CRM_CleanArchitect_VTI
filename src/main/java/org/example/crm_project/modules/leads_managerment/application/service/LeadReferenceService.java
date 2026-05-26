package org.example.crm_project.modules.leads_managerment.application.service;

import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadReferenceCatalogResponse;
import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadPageResponse;
import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadReferenceOptionResponse;

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


    LeadPageResponse<LeadReferenceOptionResponse> searchOrganizations(String q,
                                                                      Integer page,
                                                                      Integer size,
                                                                      String sortBy,
                                                                      String sortDir);
}
