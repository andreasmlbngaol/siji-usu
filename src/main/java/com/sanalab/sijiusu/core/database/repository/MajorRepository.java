package com.sanalab.sijiusu.core.database.repository;

import com.sanalab.sijiusu.core.database.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major, Long> {
    boolean existsByMajorCodeAndFacultyId(String majorCode, Long facultyId);
}
