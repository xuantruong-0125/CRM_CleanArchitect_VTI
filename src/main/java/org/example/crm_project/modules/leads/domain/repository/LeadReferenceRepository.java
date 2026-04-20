package org.example.crm_project.modules.leads.domain.repository;

import java.util.List;
import java.util.Set;

public interface LeadReferenceRepository {

    boolean existsLeadStatusById(Long statusId);

    String findLeadStatusCodeById(Long statusId);

    boolean existsLeadSourceById(Long sourceId);

    boolean existsProvinceById(Integer provinceId);

    boolean existsOrganizationById(Long organizationId);

    boolean existsCampaignById(Long campaignId);

    boolean existsUserById(Long userId);

    Set<Long> findExistingActiveProductIds(List<Long> productIds);
}