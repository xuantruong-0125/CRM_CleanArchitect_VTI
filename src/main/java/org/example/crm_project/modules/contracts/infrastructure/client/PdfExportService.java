package org.example.crm_project.modules.contracts.infrastructure.client;

/**
 * Service xuất HTML thành file PDF (Flying Saucer / iText).
 */
public interface PdfExportService {
    byte[] generatePdf(String htmlContent);
}
