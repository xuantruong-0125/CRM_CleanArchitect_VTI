package org.example.crm_project.modules.contracts.infrastructure.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.contracts.domain.constant.ContractStatus;
import org.example.crm_project.modules.contracts.domain.entity.Contract;
import org.example.crm_project.modules.contracts.domain.entity.ContractFilter;
import org.example.crm_project.modules.contracts.domain.repository.ContractRepository;
import org.example.crm_project.modules.contracts.infrastructure.persistence.entity.ContractJpaEntity;
import org.example.crm_project.modules.contracts.infrastructure.persistence.repository.ContractJpaRepository;
import org.example.crm_project.modules.contracts.infrastructure.persistence.repository.ContractSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContractRepositoryAdapter implements ContractRepository {

    private final ContractJpaRepository jpaRepository;
    private final EntityManager         entityManager;

    // ─── Save ─────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public Contract save(Contract contract) {
        if (contract.getId() == null) {
            // Create
            ContractJpaEntity entity = toJpaEntity(contract);
            return toDomainFromJpa(jpaRepository.save(entity));
        }
        // Update
        ContractJpaEntity existing = jpaRepository.findById(contract.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Contract not found: " + contract.getId()));
        updateJpaEntity(existing, contract);
        return toDomainFromJpa(jpaRepository.save(existing));
    }

    // ─── FindById — native query với JOIN ─────────────────────────────────────

    @Override
    public Optional<Contract> findById(Long id) {
        try {
            String sql = "SELECT"
                    + "  c.id, c.contract_number, c.customer_id, cust.name,"
                    + "  c.quote_id, c.template_id, c.contract_value, c.currency_code,"
                    + "  c.exchange_rate, c.status, c.start_date, c.end_date,"
                    + "  c.owner_id, u.full_name,"
                    + "  c.created_by, c.updated_by, c.created_at, c.updated_at, c.deleted_at"
                    + " FROM contracts c"
                    + " LEFT JOIN customers cust ON cust.id = c.customer_id AND cust.deleted_at IS NULL"
                    + " LEFT JOIN users u        ON u.id    = c.owner_id"
                    + " WHERE c.id = ?1 AND c.deleted_at IS NULL";

            Object[] row = (Object[]) entityManager
                    .createNativeQuery(sql)
                    .setParameter(1, id)
                    .getSingleResult();

            return Optional.of(rowToContract(row));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    // ─── FindByContractNumber ─────────────────────────────────────────────────

    @Override
    public Optional<Contract> findByContractNumber(String contractNumber) {
        return jpaRepository.findByContractNumber(contractNumber)
                .map(this::toDomainFromJpa);
    }

    // ─── FindAll — native query với JOIN + filter ─────────────────────────────

    @Override
    public Page<Contract> findAll(ContractFilter filter) {
        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());
        String statusStr = filter.getStatus() != null ? filter.getStatus().name() : null;

        return jpaRepository.searchContractsWithNames(
                emptyToNull(filter.getKeyword()),
                statusStr,
                filter.getOwnerId(),
                filter.getCustomerId(),
                filter.getStartDateFrom(),
                filter.getStartDateTo(),
                filter.getEndDateFrom(),
                filter.getEndDateTo(),
                filter.getValueFrom(),
                filter.getValueTo(),
                pageRequest
        ).map(this::projectionToDomain);
    }

    // ─── FindAllByIds ─────────────────────────────────────────────────────────

    @Override
    public List<Contract> findAllByIds(List<Long> ids) {
        return jpaRepository.findAllByIdIn(ids).stream()
                .map(this::toDomainFromJpa)
                .toList();
    }

    // ─── SoftDelete ───────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void softDelete(Long id, Long deletedBy) {
        jpaRepository.softDeleteById(id, deletedBy);
    }

    // ─── Exists ───────────────────────────────────────────────────────────────

    @Override
    public boolean existsByContractNumber(String contractNumber) {
        return jpaRepository.existsByContractNumber(contractNumber);
    }

    // ─── CountByOwnerId ───────────────────────────────────────────────────────

    @Override
    public long countByOwnerId(Long ownerId) {
        return jpaRepository.countByOwnerId(ownerId);
    }

    // ─── GetNextSequenceForPrefix ─────────────────────────────────────────────

    @Override
    public int getNextSequenceForPrefix(String prefix) {
        int maxSeq = jpaRepository.findMaxSequenceByPrefix(prefix, prefix.length());
        return maxSeq + 1;
    }

    // ─── Mappers (no MapStruct — manual to avoid Spring bean issues) ──────────

    /** JPA entity → Domain (dùng khi save trả về, findByContractNumber, findAllByIds) */
    private Contract toDomainFromJpa(ContractJpaEntity e) {
        return Contract.builder()
                .id(e.getId())
                .contractNumber(e.getContractNumber())
                .customerId(e.getCustomerId())
                .quoteId(e.getQuoteId())
                .templateId(e.getTemplateId())
                .contractValue(e.getContractValue())
                .currencyCode(e.getCurrencyCode())
                .exchangeRate(e.getExchangeRate())
                .status(e.getStatus())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .ownerId(e.getOwnerId())
                .createdBy(e.getCreatedBy())
                .updatedBy(e.getUpdatedBy())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .deletedAt(e.getDeletedAt())
                .build();
    }

    /** Domain → JPA entity (dùng khi tạo mới) */
    private ContractJpaEntity toJpaEntity(Contract c) {
        return ContractJpaEntity.builder()
                .contractNumber(c.getContractNumber())
                .customerId(c.getCustomerId())
                .quoteId(c.getQuoteId())
                .templateId(c.getTemplateId())
                .contractValue(c.getContractValue())
                .currencyCode(c.getCurrencyCode() != null ? c.getCurrencyCode() : "VND")
                .exchangeRate(c.getExchangeRate() != null ? c.getExchangeRate() : BigDecimal.ONE)
                .status(c.getStatus() != null ? c.getStatus() : ContractStatus.DRAFT)
                .startDate(c.getStartDate())
                .endDate(c.getEndDate())
                .ownerId(c.getOwnerId())
                .createdBy(c.getCreatedBy())
                .updatedBy(c.getUpdatedBy())
                .build();
    }

    /** Domain → cập nhật JPA entity (dùng khi update) */
    private void updateJpaEntity(ContractJpaEntity target, Contract src) {
        if (src.getContractNumber() != null) target.setContractNumber(src.getContractNumber());
        if (src.getCustomerId()     != null) target.setCustomerId(src.getCustomerId());
        if (src.getTemplateId()     != null) target.setTemplateId(src.getTemplateId());
        if (src.getContractValue()  != null) target.setContractValue(src.getContractValue());
        if (src.getCurrencyCode()   != null) target.setCurrencyCode(src.getCurrencyCode());
        if (src.getExchangeRate()   != null) target.setExchangeRate(src.getExchangeRate());
        if (src.getStatus()         != null) target.setStatus(src.getStatus());
        if (src.getStartDate()      != null) target.setStartDate(src.getStartDate());
        if (src.getEndDate()        != null) target.setEndDate(src.getEndDate());
        if (src.getOwnerId()        != null) target.setOwnerId(src.getOwnerId());
        if (src.getUpdatedBy()      != null) target.setUpdatedBy(src.getUpdatedBy());
    }

    /** Projection → Domain (dùng cho findAll với JOIN) */
    private Contract projectionToDomain(ContractSummaryProjection p) {
        return Contract.builder()
                .id(p.getId())
                .contractNumber(p.getContractNumber())
                .customerId(p.getCustomerId())
                .customerName(p.getCustomerName())
                .quoteId(p.getQuoteId())
                .templateId(p.getTemplateId())
                .contractValue(p.getContractValue())
                .currencyCode(p.getCurrencyCode())
                .exchangeRate(p.getExchangeRate())
                .status(p.getStatus() != null
                        ? ContractStatus.valueOf(p.getStatus())
                        : ContractStatus.DRAFT)
                .startDate(p.getStartDate())
                .endDate(p.getEndDate())
                .ownerId(p.getOwnerId())
                .ownerName(p.getOwnerName())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }

    /** Object[] từ native query → Domain (dùng cho findById) */
    private Contract rowToContract(Object[] row) {
        return Contract.builder()
                .id(toLong(row[0]))
                .contractNumber(str(row[1]))
                .customerId(toLong(row[2]))
                .customerName(str(row[3]))
                .quoteId(toLong(row[4]))
                .templateId(toLong(row[5]))
                .contractValue(toBigDecimal(row[6]))
                .currencyCode(str(row[7]))
                .exchangeRate(toBigDecimal(row[8]))
                .status(row[9] != null
                        ? ContractStatus.valueOf(row[9].toString())
                        : ContractStatus.DRAFT)
                .startDate(toLocalDate(row[10]))
                .endDate(toLocalDate(row[11]))
                .ownerId(toLong(row[12]))
                .ownerName(str(row[13]))
                .createdBy(toLong(row[14]))
                .updatedBy(toLong(row[15]))
                .createdAt(toLocalDateTime(row[16]))
                .updatedAt(toLocalDateTime(row[17]))
                .deletedAt(toLocalDateTime(row[18]))
                .build();
    }

    // ─── Type conversion helpers ──────────────────────────────────────────────

    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }

    private static Long toLong(Object o) {
        return o != null ? ((Number) o).longValue() : null;
    }

    private static String str(Object o) {
        return o != null ? o.toString() : null;
    }

    private static BigDecimal toBigDecimal(Object o) {
        return o != null ? new BigDecimal(o.toString()) : null;
    }

    private static LocalDate toLocalDate(Object o) {
        if (o == null)                          return null;
        if (o instanceof LocalDate ld)          return ld;
        if (o instanceof java.sql.Date sd)      return sd.toLocalDate();
        return LocalDate.parse(o.toString());
    }

    private static LocalDateTime toLocalDateTime(Object o) {
        if (o == null)                              return null;
        if (o instanceof LocalDateTime ldt)         return ldt;
        if (o instanceof java.sql.Timestamp ts)     return ts.toLocalDateTime();
        if (o instanceof java.util.Date d)
            return d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        return LocalDateTime.parse(o.toString());
    }
}