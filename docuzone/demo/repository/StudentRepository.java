package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Custom method: Find student by email and KTU ID (for login)
    Optional<Student> findByEmailAndKtuId(String email, String ktuId);

    // Custom method: Find student by email only
    Optional<Student> findByEmail(String email);

    // Custom method: Check if email exists
    boolean existsByEmail(String email);

    // Custom method: Check if KTU ID exists
    boolean existsByKtuId(String ktuId);
}