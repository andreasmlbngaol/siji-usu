package com.sanalab.sijiusu.siji_student.database.repository;

import com.sanalab.sijiusu.siji_student.database.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByNim(String nim);
    List<Student> findAllByNameContainingIgnoreCase(String name);

    Long countByNim(String nim);
}
