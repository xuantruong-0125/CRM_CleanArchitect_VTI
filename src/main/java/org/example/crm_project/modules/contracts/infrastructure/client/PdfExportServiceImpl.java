package org.example.crm_project.modules.contracts.infrastructure.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

/**
 * Xuất HTML → PDF sử dụng Flying Saucer (xhtmlrenderer) + iText.
 * HTML phải là XHTML hợp lệ.
 */
@Slf4j
@Component
public class PdfExportServiceImpl implements PdfExportService {

    @Override
    public byte[] generatePdf(String htmlContent) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            // Đảm bảo encoding UTF-8 và XHTML đầy đủ
            String xhtml = wrapAsXhtml(htmlContent);
            renderer.setDocumentFromString(xhtml);
            renderer.layout();
            renderer.createPDF(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            log.error("Lỗi xuất PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể tạo file PDF: " + e.getMessage(), e);
        }
    }

    private String wrapAsXhtml(String content) {
        if (content.trim().startsWith("<!DOCTYPE") || content.trim().startsWith("<html")) {
            return content;
        }
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
                    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
                <html xmlns="http://www.w3.org/1999/xhtml">
                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
                    <style>
                        body { font-family: Arial, sans-serif; font-size: 12pt; }
                        table { border-collapse: collapse; width: 100%%; }
                        th, td { border: 1px solid #333; padding: 6px 10px; }
                        th { background-color: #f0f0f0; }
                    </style>
                </head>
                <body>
                """ + content + """
                </body>
                </html>
                """;
    }
}
