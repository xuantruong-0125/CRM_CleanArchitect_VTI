package org.example.crm_project.modules.reporting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardData {
    private BigDecimal totalDoanhSo = BigDecimal.ZERO;
    private BigDecimal targetDoanhSo = BigDecimal.ZERO;
    private BigDecimal totalHoaHong = BigDecimal.ZERO;

    private MetricValue khtnMoi = new MetricValue();
    private MetricValue khtnLienHe = new MetricValue();
    private MetricValue khtnChuyenDoi = new MetricValue();
    private MetricValue khMoiMua = new MetricValue();
    private MetricValue khCuQuayLai = new MetricValue();

    private MetricValue cuocGoi = new MetricValue();
    private MetricValue cuocGap = new MetricValue();
    private MetricValue cuocGoiTongDai = new MetricValue();
    private MetricValue emailBaoGia = new MetricValue();
    private MetricValue emailGuiKh = new MetricValue();

    private List<String> monthLabels = new ArrayList<>();
    private List<Long> monthlyNewLeads = new ArrayList<>();

    private double tiLeLienHe = 0;
    private double tiLeChuyenDoi = 0;
    private double tiLeDoanhThu = 0;

    private String userName;
    private String kpiConfigName;
}
