package org.example.crm_project.modules.leads_managerment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadReferenceCatalogResponse {

    private List<LeadReferenceOptionResponse> statuses;
    private List<LeadReferenceOptionResponse> sources;
    private List<LeadReferenceOptionResponse> provinces;
    private List<LeadReferenceOptionResponse> campaigns;
}
