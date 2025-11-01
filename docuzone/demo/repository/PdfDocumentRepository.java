package com.example.demo.repository;

import com.example.demo.pdf.model.PdfDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// JpaRepository<EntityClass, PrimaryKeyDataType>
public interface PdfDocumentRepository extends JpaRepository<PdfDocument, Long> {
    // Spring Data JPA automatically provides save, findAll, findById, deleteById methods
}