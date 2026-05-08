package org.example.crm_project.modules.contacts.application.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactResponse {
    Long id;
    Long customerId;
    String fullName;
    String position;
    String email;
    String phone;
    LocalDate dob;
    String note;
    boolean primary;
    Long createdBy;
    Long updatedBy;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    boolean active;
    LocalDateTime deletedAt;
}
