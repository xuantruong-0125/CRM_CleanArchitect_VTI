package org.example.crm_project.modules.reporting.domain.constant;

import java.util.List;

public class ReportingConstants {
    public static final String MT_KHTN_MOI = "Số KHTN mới";
    public static final String MT_KHTN_LIEN_HE = "Số KHTN đang liên hệ";
    public static final String MT_KHTN_CHUYEN_DOI = "Số KHTN đã chuyển đổi";
    public static final String MT_KH_MOI_MUA = "Số KH mới mua hàng";
    public static final String MT_KH_CU_QUAY_LAI = "Số KH cũ quay lại mua";
    public static final String MT_CUOC_GOI = "Số cuộc gọi";
    public static final String MT_CUOC_GAP = "Số cuộc gặp";
    public static final String MT_CUOC_GOI_TONG_DAI = "Số cuộc gọi tổng đài";
    public static final String MT_EMAIL_BAO_GIA = "Số email báo giá";
    public static final String MT_EMAIL_GUI_KH = "Số email gửi kh";
    public static final String MT_DOANH_SO = "Doanh số";

    public static final int ACT_CALL = 1;
    public static final int ACT_MEETING = 2;
    public static final int ACT_CALL_CENTER = 3;
    public static final int ACT_EMAIL_QUOTE = 4;
    public static final int ACT_EMAIL = 5;

    public static final List<String> METRIC_ORDER = List.of(
            MT_KHTN_MOI, MT_KHTN_LIEN_HE, MT_KHTN_CHUYEN_DOI,
            MT_KH_MOI_MUA, MT_KH_CU_QUAY_LAI,
            MT_CUOC_GOI, MT_CUOC_GAP, MT_CUOC_GOI_TONG_DAI,
            MT_EMAIL_BAO_GIA, MT_EMAIL_GUI_KH, MT_DOANH_SO
    );
    
    public static final List<String> METRIC_SHORT_LABELS = List.of(
            "KH MỚI", "KH ĐANG\nLIÊN HỆ", "KH ĐÃ\nCHUYỂN ĐỔI",
            "KH MỚI\nMUA HÀNG", "KH CŨ\nQUAY LẠI",
            "CUỘC\nGỌI", "CUỘC\nGẶP", "CUỘC GỌI\nTỒNG ĐÀI",
            "EMAIL\nBÁO GIÁ", "EMAIL\nGỬI KH", "DOANH SỐ"
    );
}
