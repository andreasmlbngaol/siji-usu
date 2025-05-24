package com.sanalab.sijiusu.core.database.repository;

import com.sanalab.sijiusu.core.database.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Long> {
    boolean existsByMajorCodeAndFacultyId(String majorCode, Long facultyId);
    Optional<Major> findByFaculty_FacultyCodeAndMajorCode(String facultyCode, String majorCode);
}
