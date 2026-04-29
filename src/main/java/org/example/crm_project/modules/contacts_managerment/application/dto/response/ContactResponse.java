package org.example.crm_project.modules.contacts_managerment.application.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class ContactResponse {
    private Long id;
    private String fullName;
    private String position;
    private String phone;
    private String email;
    private String address;
    private LocalDate dob;
    private String notes;
    private boolean isPrimary;
    private boolean isActive;
    private Long customerId;
    private String customerName;
}
