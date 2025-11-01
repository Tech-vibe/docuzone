package com.example.demo.pdf.controller;

import com.example.demo.pdf.model.PdfDocument;
import com.example.demo.pdf.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/pdfs")
@CrossOrigin(origins = "*")
// Important: Add this line if your frontend runs on a different port (e.g., frontend on 8080, backend on 8081)
// @CrossOrigin(origins = "http://localhost:8080")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    // API ENDPOINT: POST /api/pdfs/upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            PdfDocument savedDoc = pdfService.uploadPdf(file);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("File uploaded successfully. ID: " + savedDoc.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: Server error.");
        }
    }

    // API ENDPOINT: GET /api/pdfs
    @GetMapping
    public ResponseEntity<List<PdfDocument>> listAllPdfs() {
        return ResponseEntity.ok(pdfService.getAllPdfs());
    }

    // API ENDPOINT: GET /api/pdfs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> downloadPdf(@PathVariable Long id) {
        return pdfService.getPdfById(id)
                .map(doc -> {
                    ByteArrayResource resource = new ByteArrayResource(doc.getData());

                    return ResponseEntity.ok()
                            // Set the content type to the file's type (should be 'application/pdf')
                            .contentType(MediaType.parseMediaType(doc.getContentType()))
                            // Set header to display PDF inline in browser
                            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + doc.getFileName() + "\"")
                            .contentLength(doc.getData().length)
                            .body(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // API ENDPOINT: DELETE /api/pdfs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePdf(@PathVariable Long id) {
        try {
            pdfService.deletePdf(id);
            return ResponseEntity.ok("PDF deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}