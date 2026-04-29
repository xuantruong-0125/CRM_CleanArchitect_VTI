package org.example.crm_project.modules.leads.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads.application.dto.request.CreateLeadRequest;
import org.example.crm_project.modules.leads.application.dto.request.ConvertLeadRequest;
import org.example.crm_project.modules.leads.application.dto.request.SearchLeadRequest;
import org.example.crm_project.modules.leads.application.dto.request.UpdateLeadRequest;
import org.example.crm_project.modules.leads.application.dto.response.ConvertLeadResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadPageResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadResponse;
import org.example.crm_project.modules.leads.application.mapper.LeadMapper;
import org.example.crm_project.modules.leads.domain.entity.Lead;
import org.example.crm_project.modules.leads.domain.entity.LeadConversionResult;
import org.example.crm_project.modules.leads.domain.entity.LeadPageResult;
import org.example.crm_project.modules.leads.domain.exception.InvalidLeadException;
import org.example.crm_project.modules.leads.domain.exception.LeadNotFoundException;
import org.example.crm_project.modules.leads.domain.repository.LeadReferenceRepository;
import org.example.crm_project.modules.leads.domain.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadServiceImpl implements LeadService {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 100;
    private static final String DEFAULT_SORT_BY = "createdAt";
    private static final String DEFAULT_SORT_DIR = "desc";
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "contactName",
            "companyName",
            "phone",
            "provinceId",
            "sourceId",
            "organizationId",
            "statusId",
            "expectedRevenue",
            "createdAt",
            "updatedAt"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern TAX_CODE_PATTERN = Pattern.compile("^\\d{10,13}$");
    private static final Pattern CITIZEN_ID_PATTERN = Pattern.compile("^\\d{12}$");

    private final LeadRepository leadRepository;
    private final LeadReferenceRepository leadReferenceRepository;

    @Override
    public LeadResponse create(CreateLeadRequest request) {
        validateCreateRequest(request);

        Lead lead = LeadMapper.toDomain(request);
        lead.setCreatedAt(LocalDateTime.now());
        lead.setUpdatedAt(LocalDateTime.now());
        lead.setIsConverted(Boolean.FALSE);

        return LeadMapper.toResponse(leadRepository.save(lead));
    }

    @Override
    public LeadResponse update(Long id, UpdateLeadRequest request) {
        validateId(id);
        validateUpdateRequest(request);

        Lead existingLead = getExistingLead(id);
        validateLeadNotConverted(existingLead, "update");
        Lead updatedLead = merge(existingLead, request);
        updatedLead.setUpdatedAt(LocalDateTime.now());

        return LeadMapper.toResponse(leadRepository.save(updatedLead));
    }

    @Override
    @Transactional(readOnly = true)
    public LeadResponse getById(Long id) {
        validateId(id);
        return LeadMapper.toResponse(getExistingLead(id));
    }

    @Override
    @Transactional(readOnly = true)
    public LeadPageResponse<LeadResponse> getAll(Integer page, Integer size, String sortBy, String sortDir) {
        PaginationOptions options = normalizePagination(page, size, sortBy, sortDir);

        LeadPageResult<Lead> pageResult = leadRepository.findAll(
                options.page(),
                options.size(),
                options.sortBy(),
                options.sortDir()
        );

        return toLeadPageResponse(pageResult);
    }

    @Override
    @Transactional(readOnly = true)
    public LeadPageResponse<LeadResponse> search(SearchLeadRequest request) {
        if (request == null) {
            throw new InvalidLeadException("Lead search request must not be null");
        }

        validateOptionalSearchId(request.getProvinceId(), "provinceId");
        validateOptionalSearchId(request.getOrganizationId(), "organizationId");
        validateOptionalSearchId(request.getSourceId(), "sourceId");
        validateOptionalSearchId(request.getStatusId(), "statusId");
        validateSearchPhone(request.getPhone());
        validateSearchEmail(request.getEmail());

        PaginationOptions options = normalizePagination(
            request.getPage(),
            request.getSize(),
            request.getSortBy(),
            request.getSortDir()
        );

        LeadPageResult<Lead> pageResult = leadRepository.search(
            request.getProvinceId(),
            request.getOrganizationId(),
            normalizeText(request.getPhone()),
            normalizeText(request.getEmail()),
            request.getStatusId(),
            request.getSourceId(),
            options.page(),
            options.size(),
            options.sortBy(),
            options.sortDir()
        );

        return toLeadPageResponse(pageResult);
    }

    @Override
    public ConvertLeadResponse convert(Long id, ConvertLeadRequest request) {
        validateId(id);
        validateConvertRequest(request);

        Lead existingLead = getExistingLead(id);
        validateLeadNotConverted(existingLead, "convert again");

        LeadConversionResult conversionResult = leadRepository.convert(id, request.getUserId());

        return ConvertLeadResponse.builder()
                .leadId(id)
                .customerId(conversionResult.getCustomerId())
                .contactId(conversionResult.getContactId())
                .opportunityId(conversionResult.getOpportunityId())
                .convertedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public void delete(Long id) {
        validateId(id);
        Lead existingLead = getExistingLead(id);
        validateLeadNotConverted(existingLead, "delete");
        leadRepository.deleteById(id);
    }

    private Lead getExistingLead(Long id) {
        return leadRepository.findById(id)
                .orElseThrow(() -> new LeadNotFoundException(id));
    }

    private Lead merge(Lead existingLead, UpdateLeadRequest request) {
        Lead updatedLead = LeadMapper.copy(existingLead);

        if (request.getContactName() != null) {
            updatedLead.setContactName(normalizeText(request.getContactName()));
        }
        if (request.getCompanyName() != null) {
            updatedLead.setCompanyName(normalizeText(request.getCompanyName()));
        }
        if (request.getPhone() != null) {
            updatedLead.setPhone(normalizeText(request.getPhone()));
        }
        if (request.getEmail() != null) {
            updatedLead.setEmail(normalizeText(request.getEmail()));
        }
        if (request.getAddress() != null) {
            updatedLead.setAddress(normalizeText(request.getAddress()));
        }
        if (request.getWebsite() != null) {
            updatedLead.setWebsite(normalizeText(request.getWebsite()));
        }
        if (request.getTaxCode() != null) {
            updatedLead.setTaxCode(normalizeText(request.getTaxCode()));
        }
        if (request.getCitizenId() != null) {
            updatedLead.setCitizenId(normalizeText(request.getCitizenId()));
        }
        if (request.getProvinceId() != null) {
            updatedLead.setProvinceId(request.getProvinceId());
        }
        if (request.getDescription() != null) {
            updatedLead.setDescription(normalizeText(request.getDescription()));
        }
        if (request.getExpectedRevenue() != null) {
            updatedLead.setExpectedRevenue(request.getExpectedRevenue());
        }
        if (request.getSourceId() != null) {
            updatedLead.setSourceId(request.getSourceId());
        }
        if (request.getCampaignId() != null) {
            updatedLead.setCampaignId(request.getCampaignId());
        }
        if (request.getOrganizationId() != null) {
            updatedLead.setOrganizationId(request.getOrganizationId());
        }
        if (request.getAssignedTo() != null) {
            updatedLead.setAssignedTo(request.getAssignedTo());
        }
        if (request.getStatusId() != null) {
            updatedLead.setStatusId(request.getStatusId());
        }
        if (request.getProductInterestIds() != null) {
            updatedLead.setProductInterestIds(request.getProductInterestIds());
        }

        return updatedLead;
    }

    private void validateCreateRequest(CreateLeadRequest request) {
        if (request == null) {
            throw new InvalidLeadException("Lead request must not be null");
        }
        if (!StringUtils.hasText(request.getContactName())) {
            throw new InvalidLeadException("contactName is required");
        }
        if (request.getStatusId() == null) {
            throw new InvalidLeadException("statusId is required");
        }

        validateFieldFormats(
                request.getPhone(),
                request.getEmail(),
                request.getTaxCode(),
                request.getCitizenId()
        );
        validateOptionalRevenue(request.getExpectedRevenue());
        validateReferences(
                request.getStatusId(),
                request.getSourceId(),
                request.getProvinceId(),
                request.getOrganizationId(),
                request.getCampaignId(),
                request.getAssignedTo(),
                request.getProductInterestIds()
        );
        validateStatusNotConverted(request.getStatusId());
    }

    private void validateUpdateRequest(UpdateLeadRequest request) {
        if (request == null) {
            throw new InvalidLeadException("Lead request must not be null");
        }

        if (request.getContactName() != null && !StringUtils.hasText(request.getContactName())) {
            throw new InvalidLeadException("contactName must not be blank");
        }

        validateFieldFormats(
                request.getPhone(),
                request.getEmail(),
                request.getTaxCode(),
                request.getCitizenId()
        );
        validateOptionalRevenue(request.getExpectedRevenue());

        validateReferences(
                request.getStatusId(),
                request.getSourceId(),
                request.getProvinceId(),
                request.getOrganizationId(),
                request.getCampaignId(),
                request.getAssignedTo(),
                request.getProductInterestIds()
        );

        if (request.getStatusId() != null) {
            validateStatusNotConverted(request.getStatusId());
        }
    }

    private void validateOptionalRevenue(BigDecimal expectedRevenue) {
        if (expectedRevenue != null && expectedRevenue.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidLeadException("expectedRevenue must be greater than or equal to 0");
        }
    }

    private void validateConvertRequest(ConvertLeadRequest request) {
        if (request == null) {
            throw new InvalidLeadException("Convert lead request must not be null");
        }
        if (request.getUserId() == null || request.getUserId() <= 0) {
            throw new InvalidLeadException("userId must be a positive number");
        }
        if (!leadReferenceRepository.existsUserById(request.getUserId())) {
            throw new InvalidLeadException("userId does not exist");
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidLeadException("id must be a positive number");
        }
    }

    private void validateOptionalSearchId(Number id, String fieldName) {
        if (id != null && id.longValue() <= 0) {
            throw new InvalidLeadException(fieldName + " must be a positive number");
        }
    }

    private void validateSearchPhone(String phone) {
        if (phone == null) {
            return;
        }

        String trimmedPhone = phone.trim();
        if (trimmedPhone.isEmpty()) {
            return;
        }

        if (!trimmedPhone.matches("^\\d{1,10}$")) {
            throw new InvalidLeadException("phone search must contain only digits and maximum 10 characters");
        }
    }

    private void validateSearchEmail(String email) {
        if (email == null) {
            return;
        }
        String trimmedEmail = email.trim();
        if (trimmedEmail.isEmpty()) {
            return;
        }
        if (trimmedEmail.length() > 100) {
            throw new InvalidLeadException("email search must be less than 100 characters");
        }
    }

    private void validateFieldFormats(String phone, String email, String taxCode, String citizenId) {
        if (phone != null && !PHONE_PATTERN.matcher(phone.trim()).matches()) {
            throw new InvalidLeadException("phone must contain exactly 10 digits");
        }

        if (email != null && !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new InvalidLeadException("email has invalid format");
        }

        if (taxCode != null && !TAX_CODE_PATTERN.matcher(taxCode.trim()).matches()) {
            throw new InvalidLeadException("taxCode must contain 10 to 13 digits");
        }

        if (citizenId != null && !CITIZEN_ID_PATTERN.matcher(citizenId.trim()).matches()) {
            throw new InvalidLeadException("citizenId must contain exactly 12 digits");
        }
    }

    private void validateReferences(
            Long statusId,
            Long sourceId,
            Integer provinceId,
            Long organizationId,
            Long campaignId,
            Long assignedTo,
            List<Long> productInterestIds
    ) {
        if (statusId != null && !leadReferenceRepository.existsLeadStatusById(statusId)) {
            throw new InvalidLeadException("statusId does not exist");
        }
        if (sourceId != null && !leadReferenceRepository.existsLeadSourceById(sourceId)) {
            throw new InvalidLeadException("sourceId does not exist");
        }
        if (provinceId != null && !leadReferenceRepository.existsProvinceById(provinceId)) {
            throw new InvalidLeadException("provinceId does not exist");
        }
        if (organizationId != null && !leadReferenceRepository.existsOrganizationById(organizationId)) {
            throw new InvalidLeadException("organizationId does not exist");
        }
        if (campaignId != null && !leadReferenceRepository.existsCampaignById(campaignId)) {
            throw new InvalidLeadException("campaignId does not exist");
        }
        if (assignedTo != null && !leadReferenceRepository.existsUserById(assignedTo)) {
            throw new InvalidLeadException("assignedTo does not exist");
        }

        if (productInterestIds != null && !productInterestIds.isEmpty()) {
            if (productInterestIds.stream().anyMatch(productId -> productId == null || productId <= 0)) {
                throw new InvalidLeadException("productInterestIds contains invalid id");
            }

            Set<Long> existingProductIds = leadReferenceRepository.findExistingActiveProductIds(productInterestIds);
            if (existingProductIds.size() != productInterestIds.stream().distinct().count()) {
                throw new InvalidLeadException("productInterestIds contains non-existing or inactive product");
            }
        }
    }

    private void validateStatusNotConverted(Long statusId) {
        String statusCode = leadReferenceRepository.findLeadStatusCodeById(statusId);
        if ("CONVERTED".equals(statusCode)) {
            throw new InvalidLeadException("statusId CONVERTED is reserved for lead conversion use case");
        }
    }

    private void validateLeadNotConverted(Lead lead, String action) {
        if (Boolean.TRUE.equals(lead.getIsConverted())) {
            throw new InvalidLeadException("Converted lead cannot be " + action);
        }
    }

    private LeadPageResponse<LeadResponse> toLeadPageResponse(LeadPageResult<Lead> pageResult) {
        List<LeadResponse> content = pageResult.getContent().stream()
                .map(LeadMapper::toResponse)
                .toList();

        return LeadPageResponse.<LeadResponse>builder()
                .content(content)
                .page(pageResult.getPage())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .hasNext(pageResult.isHasNext())
                .hasPrevious(pageResult.isHasPrevious())
                .build();
    }

    private PaginationOptions normalizePagination(Integer page, Integer size, String sortBy, String sortDir) {
        int normalizedPage = page == null ? DEFAULT_PAGE : page;
        int normalizedSize = size == null ? DEFAULT_SIZE : size;

        if (normalizedPage < 0) {
            throw new InvalidLeadException("page must be greater than or equal to 0");
        }
        if (normalizedSize <= 0 || normalizedSize > MAX_SIZE) {
            throw new InvalidLeadException("size must be between 1 and " + MAX_SIZE);
        }

        String normalizedSortBy = StringUtils.hasText(sortBy) ? sortBy.trim() : DEFAULT_SORT_BY;
        if (!ALLOWED_SORT_FIELDS.contains(normalizedSortBy)) {
            throw new InvalidLeadException("sortBy is not supported");
        }

        String normalizedSortDir = StringUtils.hasText(sortDir) ? sortDir.trim().toLowerCase() : DEFAULT_SORT_DIR;
        if (!"asc".equals(normalizedSortDir) && !"desc".equals(normalizedSortDir)) {
            throw new InvalidLeadException("sortDir must be either asc or desc");
        }

        return new PaginationOptions(normalizedPage, normalizedSize, normalizedSortBy, normalizedSortDir);
    }

    private String normalizeText(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return value.trim();
    }

    private record PaginationOptions(int page, int size, String sortBy, String sortDir) {
    }
}