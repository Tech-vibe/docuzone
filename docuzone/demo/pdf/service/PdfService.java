package com.example.demo.pdf.service;

import com.example.demo.pdf.model.PdfDocument;
import com.example.demo.repository.PdfDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PdfService {

    @Autowired
    private PdfDocumentRepository repository;

    // 1. UPLOAD PDF
    public PdfDocument uploadPdf(MultipartFile file) throws IOException {
        if (file.isEmpty() || !file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("File must be a non-empty PDF.");
        }

        PdfDocument doc = new PdfDocument(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes() // Gets the binary data
        );

        return repository.save(doc);
    }

    // 2. VIEW/LIST ALL PDFS (Metadata)
    public List<PdfDocument> getAllPdfs() {
        return repository.findAll();
    }

    // 3. VIEW SPECIFIC PDF (Binary Data)
    public Optional<PdfDocument> getPdfById(Long id) {
        return repository.findById(id);
    }

    // 4. DELETE PDF
    public void deletePdf(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("PDF with ID " + id + " not found.");
        }
        repository.deleteById(id);
    }
}