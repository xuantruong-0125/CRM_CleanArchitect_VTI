package org.example.crm_project.modules.customers_managerment.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO: CreateAttachmentDTO
 * Create/Update DTO for Attachment
 */
@Data
public class CreateAttachmentDTO {
    @NotBlank(message = "Tên file không được để trống")
    private String fileName;

    private String fileType;
    private Integer fileSize;
    private String filePath;
    
    @NotNull(message = "Loại liên quan không được để trống")
    private String relatedToType;
    
    @NotNull(message = "ID liên quan không được để trống")
    private Long relatedToId;

    private Long uploadedBy;
}
