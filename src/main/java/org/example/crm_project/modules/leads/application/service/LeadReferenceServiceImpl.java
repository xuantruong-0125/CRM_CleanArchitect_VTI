package org.example.crm_project.modules.leads.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads.application.dto.response.LeadPageResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadReferenceCatalogResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadReferenceOptionResponse;
import org.example.crm_project.modules.leads.application.mapper.LeadReferenceMapper;
import org.example.crm_project.modules.leads.domain.exception.InvalidLeadException;
import org.example.crm_project.modules.leads.domain.repository.LeadReferenceCatalogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeadReferenceServiceImpl implements LeadReferenceService {

    private final LeadReferenceCatalogRepository leadReferenceCatalogRepository;

    @Override
    public LeadReferenceCatalogResponse getReferenceCatalog() {
        return LeadReferenceCatalogResponse.builder()
                .statuses(leadReferenceCatalogRepository.findLeadStatuses().stream()
                        .map(LeadReferenceMapper::toResponse)
                        .toList())
                .sources(leadReferenceCatalogRepository.findLeadSources().stream()
                        .map(LeadReferenceMapper::toResponse)
                        .toList())
                .build();
    }

        @Override
        public LeadPageResponse<LeadReferenceOptionResponse> searchAssignees(String q,
                                                                                                                                                 Long organizationId,
                                                                                                                                                 Long roleId,
                                                                                                                                                 String status,
                                                                                                                                                 Integer page,
                                                                                                                                                 Integer size,
                                                                                                                                                 String sortBy,
                                                                                                                                                 String sortDir) {
                int safePage = normalizePage(page);
                int safeSize = normalizeSize(size);

                String safeStatus = hasText(status) ? status.trim().toUpperCase() : "ACTIVE";
                String safeSortBy = hasText(sortBy) ? sortBy.trim() : "fullName";
                String safeSortDir = normalizeSortDir(sortDir);

                var content = leadReferenceCatalogRepository.searchAssignees(
                                                trimToNull(q),
                                                organizationId,
                                                roleId,
                                                safeStatus,
                                                safePage,
                                                safeSize,
                                                safeSortBy,
                                                safeSortDir)
                                .stream()
                                .map(LeadReferenceMapper::toResponse)
                                .toList();

                long totalElements = leadReferenceCatalogRepository.countAssignees(trimToNull(q), organizationId, roleId, safeStatus);
                int totalPages = computeTotalPages(totalElements, safeSize);

                return LeadPageResponse.<LeadReferenceOptionResponse>builder()
                                .content(content)
                                .page(safePage)
                                .size(safeSize)
                                .totalElements(totalElements)
                                .totalPages(totalPages)
                                .hasNext(safePage + 1 < totalPages)
                                .hasPrevious(safePage > 0)
                                .build();
        }

        @Override
        public LeadPageResponse<LeadReferenceOptionResponse> searchProducts(String q,
                                                                                                                                                String type,
                                                                                                                                                Long categoryId,
                                                                                                                                                Boolean isActive,
                                                                                                                                                Integer page,
                                                                                                                                                Integer size,
                                                                                                                                                String sortBy,
                                                                                                                                                String sortDir) {
                int safePage = normalizePage(page);
                int safeSize = normalizeSize(size);

                String safeType = hasText(type) ? type.trim().toUpperCase() : null;
                String safeSortBy = hasText(sortBy) ? sortBy.trim() : "name";
                String safeSortDir = normalizeSortDir(sortDir);

                var content = leadReferenceCatalogRepository.searchProducts(
                                                trimToNull(q),
                                                safeType,
                                                categoryId,
                                                isActive == null ? Boolean.TRUE : isActive,
                                                safePage,
                                                safeSize,
                                                safeSortBy,
                                                safeSortDir)
                                .stream()
                                .map(LeadReferenceMapper::toResponse)
                                .toList();

                long totalElements = leadReferenceCatalogRepository.countProducts(
                                trimToNull(q),
                                safeType,
                                categoryId,
                                isActive == null ? Boolean.TRUE : isActive
                );
                int totalPages = computeTotalPages(totalElements, safeSize);

                return LeadPageResponse.<LeadReferenceOptionResponse>builder()
                                .content(content)
                                .page(safePage)
                                .size(safeSize)
                                .totalElements(totalElements)
                                .totalPages(totalPages)
                                .hasNext(safePage + 1 < totalPages)
                                .hasPrevious(safePage > 0)
                                .build();
        }

        @Override
        public LeadPageResponse<LeadReferenceOptionResponse> searchProvinces(String q,
                                                                                                                                                 String code,
                                                                                                                                                 Integer page,
                                                                                                                                                 Integer size,
                                                                                                                                                 String sortBy,
                                                                                                                                                 String sortDir) {
                int safePage = normalizePage(page);
                int safeSize = normalizeSize(size);
                String safeSortBy = hasText(sortBy) ? sortBy.trim() : "name";
                String safeSortDir = normalizeSortDir(sortDir);

                var content = leadReferenceCatalogRepository.searchProvinces(
                                                trimToNull(q),
                                                trimToNull(code),
                                                safePage,
                                                safeSize,
                                                safeSortBy,
                                                safeSortDir)
                                .stream()
                                .map(LeadReferenceMapper::toResponse)
                                .toList();

                long totalElements = leadReferenceCatalogRepository.countProvinces(trimToNull(q), trimToNull(code));
                int totalPages = computeTotalPages(totalElements, safeSize);

                return LeadPageResponse.<LeadReferenceOptionResponse>builder()
                                .content(content)
                                .page(safePage)
                                .size(safeSize)
                                .totalElements(totalElements)
                                .totalPages(totalPages)
                                .hasNext(safePage + 1 < totalPages)
                                .hasPrevious(safePage > 0)
                                .build();
        }

        private int normalizePage(Integer page) {
                return page == null || page < 0 ? 0 : page;
        }

        private int normalizeSize(Integer size) {
                if (size == null) {
                        return 20;
                }
                if (size < 1 || size > 100) {
                        throw new InvalidLeadException("size must be between 1 and 100");
                }
                return size;
        }

        private String normalizeSortDir(String sortDir) {
                if (!hasText(sortDir)) {
                        return "asc";
                }

                String value = sortDir.trim().toLowerCase();
                if (!value.equals("asc") && !value.equals("desc")) {
                        throw new InvalidLeadException("sortDir must be asc or desc");
                }

                return value;
        }

        private int computeTotalPages(long totalElements, int size) {
                if (totalElements == 0) {
                        return 0;
                }
                return (int) ((totalElements + size - 1) / size);
        }

        private String trimToNull(String value) {
                if (!hasText(value)) {
                        return null;
                }
                return value.trim();
        }

        private boolean hasText(String value) {
                return value != null && !value.trim().isEmpty();
        }
}