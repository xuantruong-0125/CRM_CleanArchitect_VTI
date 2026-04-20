package org.example.crm_project.modules.leads.application.service;

import org.example.crm_project.modules.leads.application.dto.request.CreateLeadRequest;
import org.example.crm_project.modules.leads.application.dto.request.ConvertLeadRequest;
import org.example.crm_project.modules.leads.application.dto.request.SearchLeadRequest;
import org.example.crm_project.modules.leads.application.dto.request.UpdateLeadRequest;
import org.example.crm_project.modules.leads.application.dto.response.ConvertLeadResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadPageResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadResponse;

import java.util.List;

public interface LeadService {

    LeadResponse create(CreateLeadRequest request);

    LeadResponse update(Long id, UpdateLeadRequest request);

    LeadResponse getById(Long id);

    LeadPageResponse<LeadResponse> getAll(Integer page, Integer size, String sortBy, String sortDir);

    LeadPageResponse<LeadResponse> search(SearchLeadRequest request);

    ConvertLeadResponse convert(Long id, ConvertLeadRequest request);

    void delete(Long id);
}