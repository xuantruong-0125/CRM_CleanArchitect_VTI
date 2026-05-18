package org.example.crm_project.modules.activity_management.domain.repository;

import java.util.List;

/**
 * Dùng Record để đảm bảo tính bất biến (Immutable) 
 * và sạch bóng Framework/Library trong Domain.
 */
public record PagedResult<T>(
    List<T> content,
    long totalElements,
    int totalPages
) {}