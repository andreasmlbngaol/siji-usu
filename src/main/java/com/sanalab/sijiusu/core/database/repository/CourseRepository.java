package com.sanalab.sijiusu.core.database.repository;

import com.sanalab.sijiusu.core.database.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@SuppressWarnings("unused")
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByNameContainingIgnoreCase(String name); // Untuk mencari course berdasarkan nama yang mengandung string tertentu
}
