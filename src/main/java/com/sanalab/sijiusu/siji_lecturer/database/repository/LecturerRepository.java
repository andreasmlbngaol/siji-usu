package com.sanalab.sijiusu.siji_lecturer.database.repository;

import com.sanalab.sijiusu.siji_lecturer.database.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Optional<Lecturer> findByNip(String nip);
    Optional<Lecturer> findByNidn(String nidn);

    List<Lecturer> findAllByNameContainingIgnoreCase(String name);

    Boolean existsByNip(String nip);
    Boolean existsByNidn(String nidn);
}
