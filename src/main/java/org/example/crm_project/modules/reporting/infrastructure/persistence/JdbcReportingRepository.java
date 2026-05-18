package org.example.crm_project.modules.reporting.infrastructure.persistence;

import org.example.crm_project.modules.reporting.domain.constant.ReportingConstants;
import org.example.crm_project.modules.reporting.domain.model.DashboardData;
import org.example.crm_project.modules.reporting.domain.model.MetricValue;
import org.example.crm_project.modules.reporting.domain.model.UserKpiRow;
import org.example.crm_project.modules.reporting.domain.repository.ReportingRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcReportingRepository implements ReportingRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReportingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class KpiConfigInfo {
        Long id;
        String name;
        LocalDate startDate;
        LocalDate endDate;
    }

    private KpiConfigInfo getConfigInfo(Long kpiConfigId) {
        String sql = "SELECT id, name, start_date, end_date FROM kpi_configs WHERE id = ?";
        List<KpiConfigInfo> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            KpiConfigInfo info = new KpiConfigInfo();
            info.id = rs.getLong("id");
            info.name = rs.getString("name");
            if (rs.getDate("start_date") != null) {
                info.startDate = rs.getDate("start_date").toLocalDate();
            }
            if (rs.getDate("end_date") != null) {
                info.endDate = rs.getDate("end_date").toLocalDate();
            }
            return info;
        }, kpiConfigId);
        return list.isEmpty() ? null : list.get(0);
    }

    private Map<String, BigDecimal> getTargets(Long kpiConfigId) {
        String sql = "SELECT metric_type, target_value FROM kpi_targets WHERE kpi_config_id = ?";
        Map<String, BigDecimal> targetMap = new LinkedHashMap<>();
        jdbcTemplate.query(sql, (rs, rowNum) -> {
            String metricType = rs.getString("metric_type");
            BigDecimal targetValue = rs.getBigDecimal("target_value");
            targetMap.put(metricType, targetValue != null ? targetValue : BigDecimal.ZERO);
            return null;
        }, kpiConfigId);
        return targetMap;
    }

    private static class AssignmentInfo {
        Long userId;
        Long organizationId;
        BigDecimal commissionPercent;
        String userName;
        String organizationName;
    }

    private List<AssignmentInfo> getAssignments(Long kpiConfigId) {
        String sql = "SELECT ta.user_id, ta.organization_id, ta.commission_percent, u.full_name, u.username, " +
                "COALESCE(o1.name, o2.name) as org_name " +
                "FROM target_assignments ta " +
                "LEFT JOIN users u ON ta.user_id = u.id " +
                "LEFT JOIN organizations o1 ON u.organization_id = o1.id " +
                "LEFT JOIN organizations o2 ON ta.organization_id = o2.id " +
                "WHERE ta.kpi_config_id = ?";
        List<AssignmentInfo> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            AssignmentInfo info = new AssignmentInfo();
            info.userId = rs.getObject("user_id", Long.class);
            info.organizationId = rs.getObject("organization_id", Long.class);
            info.commissionPercent = rs.getBigDecimal("commission_percent");
            String fullName = rs.getString("full_name");
            info.userName = fullName != null ? fullName : rs.getString("username");
            info.organizationName = rs.getString("org_name");
            return info;
        }, kpiConfigId);

        List<AssignmentInfo> expandedList = new ArrayList<>();
        for (AssignmentInfo info : list) {
            if (info.userId == null && info.organizationId != null) {
                String usersSql = "SELECT id, full_name, username FROM users WHERE organization_id = ? AND deleted_at IS NULL";
                jdbcTemplate.query(usersSql, (rs, rowNum) -> {
                    AssignmentInfo expanded = new AssignmentInfo();
                    expanded.userId = rs.getLong("id");
                    expanded.organizationId = info.organizationId;
                    expanded.commissionPercent = info.commissionPercent;
                    String fName = rs.getString("full_name");
                    expanded.userName = fName != null ? fName : rs.getString("username");
                    expanded.organizationName = info.organizationName;
                    expandedList.add(expanded);
                    return null;
                }, info.organizationId);
            } else if (info.userId != null) {
                expandedList.add(info);
            }
        }
        return expandedList;
    }

    @Override
    public List<UserKpiRow> getDetailReport(Long kpiConfigId) {
        KpiConfigInfo config = getConfigInfo(kpiConfigId);
        if (config == null) return Collections.emptyList();

        Map<String, BigDecimal> targetMap = getTargets(kpiConfigId);
        List<AssignmentInfo> assignments = getAssignments(kpiConfigId);
        List<UserKpiRow> rows = new ArrayList<>();

        for (AssignmentInfo asn : assignments) {
            UserKpiRow row = new UserKpiRow();
            row.setUserId(asn.userId);
            row.setUserName(asn.userName != null ? asn.userName : "");
            row.setOrganizationName(asn.organizationName != null ? asn.organizationName : "");

            List<MetricValue> metricValues = new ArrayList<>();
            for (String metricType : ReportingConstants.METRIC_ORDER) {
                BigDecimal actual = computeActual(metricType, asn.userId, config.startDate, config.endDate);
                BigDecimal target = targetMap.getOrDefault(metricType, BigDecimal.ZERO);
                metricValues.add(new MetricValue(actual, target));
            }
            row.setMetricValues(metricValues);

            MetricValue ds = metricValues.size() > 10 ? metricValues.get(10) : new MetricValue();
            row.setDoanhSo(ds);

            BigDecimal commission = asn.commissionPercent != null ? asn.commissionPercent : BigDecimal.ZERO;
            BigDecimal hoaHong = ds.getActual().multiply(commission).divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
            row.setHoaHong(hoaHong);

            rows.add(row);
        }
        return rows;
    }

    @Override
    public DashboardData getDashboardData(Long kpiConfigId, Long userId) {
        KpiConfigInfo config = getConfigInfo(kpiConfigId);
        if (config == null) return new DashboardData();

        DashboardData data = new DashboardData();
        data.setKpiConfigName(config.name);

        Map<String, BigDecimal> targetMap = getTargets(kpiConfigId);
        List<AssignmentInfo> allAsn = getAssignments(kpiConfigId);
        
        List<Long> userIds = new ArrayList<>();
        BigDecimal commissionPercent = BigDecimal.ZERO;

        if (userId != null) {
            userIds.add(userId);
            AssignmentInfo matchingAsn = allAsn.stream()
                    .filter(a -> a.userId != null && a.userId.equals(userId))
                    .findFirst().orElse(null);
            if (matchingAsn != null) {
                data.setUserName(matchingAsn.userName);
                if (matchingAsn.commissionPercent != null) {
                    commissionPercent = matchingAsn.commissionPercent;
                }
            }
        } else {
            for (AssignmentInfo a : allAsn) {
                if (a.userId != null) userIds.add(a.userId);
                if (a.commissionPercent != null) {
                    commissionPercent = commissionPercent.add(a.commissionPercent);
                }
            }
            userIds = userIds.stream().distinct().collect(Collectors.toList());
            data.setUserName("Tất cả nhân viên");
            if (!allAsn.isEmpty()) {
                commissionPercent = commissionPercent.divide(BigDecimal.valueOf(allAsn.size()), 2, RoundingMode.HALF_UP);
            }
        }

        BigDecimal totalActualKhtnMoi = BigDecimal.ZERO, totalActualLienHe = BigDecimal.ZERO,
                totalActualChuyenDoi = BigDecimal.ZERO, totalActualMoiMua = BigDecimal.ZERO,
                totalActualCuQuayLai = BigDecimal.ZERO, totalActualCuocGoi = BigDecimal.ZERO,
                totalActualCuocGap = BigDecimal.ZERO, totalActualTongDai = BigDecimal.ZERO,
                totalActualEmailBaoGia = BigDecimal.ZERO, totalActualEmailGuiKh = BigDecimal.ZERO,
                totalActualDoanhSo = BigDecimal.ZERO;

        for (Long uid : userIds) {
            totalActualKhtnMoi = totalActualKhtnMoi.add(computeActual(ReportingConstants.MT_KHTN_MOI, uid, config.startDate, config.endDate));
            totalActualLienHe = totalActualLienHe.add(computeActual(ReportingConstants.MT_KHTN_LIEN_HE, uid, config.startDate, config.endDate));
            totalActualChuyenDoi = totalActualChuyenDoi.add(computeActual(ReportingConstants.MT_KHTN_CHUYEN_DOI, uid, config.startDate, config.endDate));
            totalActualMoiMua = totalActualMoiMua.add(computeActual(ReportingConstants.MT_KH_MOI_MUA, uid, config.startDate, config.endDate));
            totalActualCuQuayLai = totalActualCuQuayLai.add(computeActual(ReportingConstants.MT_KH_CU_QUAY_LAI, uid, config.startDate, config.endDate));
            totalActualCuocGoi = totalActualCuocGoi.add(computeActual(ReportingConstants.MT_CUOC_GOI, uid, config.startDate, config.endDate));
            totalActualCuocGap = totalActualCuocGap.add(computeActual(ReportingConstants.MT_CUOC_GAP, uid, config.startDate, config.endDate));
            totalActualTongDai = totalActualTongDai.add(computeActual(ReportingConstants.MT_CUOC_GOI_TONG_DAI, uid, config.startDate, config.endDate));
            totalActualEmailBaoGia = totalActualEmailBaoGia.add(computeActual(ReportingConstants.MT_EMAIL_BAO_GIA, uid, config.startDate, config.endDate));
            totalActualEmailGuiKh = totalActualEmailGuiKh.add(computeActual(ReportingConstants.MT_EMAIL_GUI_KH, uid, config.startDate, config.endDate));
            totalActualDoanhSo = totalActualDoanhSo.add(computeActual(ReportingConstants.MT_DOANH_SO, uid, config.startDate, config.endDate));
        }

        BigDecimal multiplier = BigDecimal.valueOf(userIds.isEmpty() ? 1 : userIds.size());

        data.setKhtnMoi(new MetricValue(totalActualKhtnMoi, targetMap.getOrDefault(ReportingConstants.MT_KHTN_MOI, BigDecimal.ZERO).multiply(multiplier)));
        data.setKhtnLienHe(new MetricValue(totalActualLienHe, targetMap.getOrDefault(ReportingConstants.MT_KHTN_LIEN_HE, BigDecimal.ZERO).multiply(multiplier)));
        data.setKhtnChuyenDoi(new MetricValue(totalActualChuyenDoi, targetMap.getOrDefault(ReportingConstants.MT_KHTN_CHUYEN_DOI, BigDecimal.ZERO).multiply(multiplier)));
        data.setKhMoiMua(new MetricValue(totalActualMoiMua, targetMap.getOrDefault(ReportingConstants.MT_KH_MOI_MUA, BigDecimal.ZERO).multiply(multiplier)));
        data.setKhCuQuayLai(new MetricValue(totalActualCuQuayLai, targetMap.getOrDefault(ReportingConstants.MT_KH_CU_QUAY_LAI, BigDecimal.ZERO).multiply(multiplier)));
        data.setCuocGoi(new MetricValue(totalActualCuocGoi, targetMap.getOrDefault(ReportingConstants.MT_CUOC_GOI, BigDecimal.ZERO).multiply(multiplier)));
        data.setCuocGap(new MetricValue(totalActualCuocGap, targetMap.getOrDefault(ReportingConstants.MT_CUOC_GAP, BigDecimal.ZERO).multiply(multiplier)));
        data.setCuocGoiTongDai(new MetricValue(totalActualTongDai, targetMap.getOrDefault(ReportingConstants.MT_CUOC_GOI_TONG_DAI, BigDecimal.ZERO).multiply(multiplier)));
        data.setEmailBaoGia(new MetricValue(totalActualEmailBaoGia, targetMap.getOrDefault(ReportingConstants.MT_EMAIL_BAO_GIA, BigDecimal.ZERO).multiply(multiplier)));
        data.setEmailGuiKh(new MetricValue(totalActualEmailGuiKh, targetMap.getOrDefault(ReportingConstants.MT_EMAIL_GUI_KH, BigDecimal.ZERO).multiply(multiplier)));

        data.setTotalDoanhSo(totalActualDoanhSo);
        BigDecimal targetDs = targetMap.getOrDefault(ReportingConstants.MT_DOANH_SO, BigDecimal.ZERO).multiply(multiplier);
        data.setTargetDoanhSo(targetDs);
        data.setTotalHoaHong(totalActualDoanhSo.multiply(commissionPercent).divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP));

        BigDecimal targetLienHe = targetMap.getOrDefault(ReportingConstants.MT_KHTN_LIEN_HE, BigDecimal.ZERO).multiply(multiplier);
        BigDecimal totalKhtnMoiVaLienHe = targetMap.getOrDefault(ReportingConstants.MT_KHTN_MOI, BigDecimal.ZERO).multiply(multiplier).add(targetLienHe);
        data.setTiLeLienHe(totalKhtnMoiVaLienHe.compareTo(BigDecimal.ZERO) > 0
                ? totalActualLienHe.multiply(BigDecimal.valueOf(100)).divide(totalKhtnMoiVaLienHe, 2, RoundingMode.HALF_UP).doubleValue() : 0);

        BigDecimal targetChuyenDoi = targetMap.getOrDefault(ReportingConstants.MT_KHTN_CHUYEN_DOI, BigDecimal.ZERO).multiply(multiplier);
        data.setTiLeChuyenDoi(targetChuyenDoi.compareTo(BigDecimal.ZERO) > 0
                ? totalActualChuyenDoi.multiply(BigDecimal.valueOf(100)).divide(targetChuyenDoi, 2, RoundingMode.HALF_UP).doubleValue() : 0);

        data.setTiLeDoanhThu(targetDs.compareTo(BigDecimal.ZERO) > 0
                ? totalActualDoanhSo.multiply(BigDecimal.valueOf(100)).divide(targetDs, 2, RoundingMode.HALF_UP).doubleValue() : 0);

        computeMonthlyLeads(data, userIds, config.startDate, config.endDate);

        return data;
    }

    private BigDecimal computeActual(String metricType, Long userId, LocalDate startDate, LocalDate endDate) {
        try {
            String start = startDate != null ? startDate.toString() : "2000-01-01";
            String end = endDate != null ? endDate.toString() : "2099-12-31";

            return switch (metricType) {
                case ReportingConstants.MT_KHTN_MOI -> countLeadsByStatus(userId, 1, start, end); // Assuming status_id 1 is NEW
                case ReportingConstants.MT_KHTN_LIEN_HE -> countLeadsByStatus(userId, 2, start, end); // Assuming status_id 2 is CONTACTING
                case ReportingConstants.MT_KHTN_CHUYEN_DOI -> countConvertedLeads(userId, start, end);
                case ReportingConstants.MT_KH_MOI_MUA -> countNewCustomers(userId, start, end);
                case ReportingConstants.MT_KH_CU_QUAY_LAI -> countReturningCustomers(userId, start, end);
                case ReportingConstants.MT_CUOC_GOI -> countActivities(userId, ReportingConstants.ACT_CALL, start, end);
                case ReportingConstants.MT_CUOC_GAP -> countActivities(userId, ReportingConstants.ACT_MEETING, start, end);
                case ReportingConstants.MT_CUOC_GOI_TONG_DAI -> countActivities(userId, ReportingConstants.ACT_CALL_CENTER, start, end);
                case ReportingConstants.MT_EMAIL_BAO_GIA -> countActivities(userId, ReportingConstants.ACT_EMAIL_QUOTE, start, end);
                case ReportingConstants.MT_EMAIL_GUI_KH -> countActivities(userId, ReportingConstants.ACT_EMAIL, start, end);
                case ReportingConstants.MT_DOANH_SO -> sumRevenue(userId, start, end);
                default -> BigDecimal.ZERO;
            };
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal countLeadsByStatus(Long userId, int statusId, String start, String end) {
        String sql = "SELECT COUNT(*) FROM leads WHERE assigned_to = ? AND status_id = ? AND created_at >= ? AND created_at <= ? AND is_converted = 0";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, userId, statusId, start, end + " 23:59:59");
        return BigDecimal.valueOf(count != null ? count : 0);
    }

    private BigDecimal countConvertedLeads(Long userId, String start, String end) {
        String sql = "SELECT COUNT(*) FROM leads WHERE assigned_to = ? AND is_converted = 1 AND converted_at >= ? AND converted_at <= ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, userId, start, end + " 23:59:59");
        return BigDecimal.valueOf(count != null ? count : 0);
    }

    private BigDecimal countNewCustomers(Long userId, String start, String end) {
        String sql = "SELECT COUNT(*) FROM customers WHERE assigned_to = ? AND created_at >= ? AND created_at <= ? AND deleted_at IS NULL";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, userId, start, end + " 23:59:59");
        return BigDecimal.valueOf(count != null ? count : 0);
    }

    private BigDecimal countReturningCustomers(Long userId, String start, String end) {
        String sql = "SELECT COUNT(DISTINCT o.customer_id) FROM orders o JOIN customers c ON o.customer_id = c.id " +
                "WHERE c.assigned_to = ? AND o.created_at >= ? AND o.created_at <= ? AND o.deleted_at IS NULL AND c.deleted_at IS NULL AND c.created_at < ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, userId, start, end + " 23:59:59", start);
        return BigDecimal.valueOf(count != null ? count : 0);
    }

    private BigDecimal countActivities(Long userId, int activityType, String start, String end) {
        String sql = "SELECT COUNT(*) FROM activities WHERE performed_by = ? AND activity_type = ? AND created_at >= ? AND created_at <= ? AND deleted_at IS NULL";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, userId, activityType, start, end + " 23:59:59");
        return BigDecimal.valueOf(count != null ? count : 0);
    }

    private BigDecimal sumRevenue(Long userId, String start, String end) {
        String sql = "SELECT IFNULL(SUM(o.total_amount), 0) FROM orders o JOIN customers c ON o.customer_id = c.id " +
                "WHERE c.assigned_to = ? AND o.status = 'COMPLETED' AND o.created_at >= ? AND o.created_at <= ? AND o.deleted_at IS NULL";
        BigDecimal sum = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId, start, end + " 23:59:59");
        return sum != null ? sum : BigDecimal.ZERO;
    }

    private void computeMonthlyLeads(DashboardData data, List<Long> userIds, LocalDate startDate, LocalDate endDate) {
        List<String> labels = new ArrayList<>();
        List<Long> counts = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate from = now.minusMonths(11).withDayOfMonth(1);

        for (int i = 0; i < 12; i++) {
            LocalDate monthStart = from.plusMonths(i);
            LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);
            labels.add(monthStart.format(DateTimeFormatter.ofPattern("M/yyyy")));

            long total = 0;
            for (Long uid : userIds) {
                try {
                    String sql = "SELECT COUNT(*) FROM leads WHERE assigned_to = ? AND created_at >= ? AND created_at <= ? AND deleted_at IS NULL";
                    Long count = jdbcTemplate.queryForObject(sql, Long.class, uid, monthStart.toString(), monthEnd.toString() + " 23:59:59");
                    total += (count != null ? count : 0);
                } catch (Exception ignored) {}
            }
            counts.add(total);
        }
        data.setMonthLabels(labels);
        data.setMonthlyNewLeads(counts);
    }
}
