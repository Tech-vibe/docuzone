package com.example.demo.pdf.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pdf_document")
public class PdfDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "content_type")
    private String contentType;

    @Lob // Marks this field for storing large objects
    @Column(name = "data", columnDefinition="LONGBLOB", nullable = false)
    private byte[] data;

    // --- Constructor ---
    public PdfDocument() {
    }
    public PdfDocument(String fileName, String contentType, byte[] data) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.data = data;
    }

    // --- Getters and Setters (REQUIRED) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
}