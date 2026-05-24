package org.example.crm_project.modules.invoices.application.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class BulkActionRequest {
    private List<Long> invoiceIds;
    private Long assignToUserId;
}