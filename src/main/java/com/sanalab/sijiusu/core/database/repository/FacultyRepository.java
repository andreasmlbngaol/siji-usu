package com.sanalab.sijiusu.core.database.repository;

import com.sanalab.sijiusu.core.database.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Boolean existsByFacultyCode(String facultyCode);
}
