package org.example.crm_project.modules.leads.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchLeadRequest {

    private Integer provinceId;
    private Long organizationId;
    private String phone;
    private String email;
    private Long statusId;
    private Long sourceId;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortDir;
}