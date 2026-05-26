package org.example.crm_project.modules.contracts.application.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BulkActionRequest {

    @NotEmpty(message = "Danh sách ID không được để trống")
    private List<Long> ids;

    @NotNull(message = "Hành động không được để trống")
    private BulkAction action;

    private Long assignToUserId; // dùng khi action = ASSIGN

    public enum BulkAction {
        ASSIGN, DELETE
    }
}
