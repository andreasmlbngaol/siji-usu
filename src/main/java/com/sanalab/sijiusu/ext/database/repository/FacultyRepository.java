package com.sanalab.sijiusu.ext.database.repository;

import com.sanalab.sijiusu.ext.database.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Boolean existsByFacultyCode(String facultyCode);
}
