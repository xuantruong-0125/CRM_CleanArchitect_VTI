package org.example.crm_project.modules.invoices.infrastructure.client;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfGenerationService {
    private final SpringTemplateEngine stringTemplateEngine;

    public byte[] generatePdfFromHtmlString(String htmlContent, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        String processedHtml = stringTemplateEngine.process(htmlContent, context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(processedHtml, "http://localhost:8080/");
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi xuất PDF: " + e.getMessage(), e);
        }
    }
}