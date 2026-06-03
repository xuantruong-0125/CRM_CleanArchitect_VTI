package org.example.crm_project.modules.customers_managerment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadAttachmentResponseDTO {
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String filePath;
}
