package com.sanalab.sijiusu.core.database.repository;

import com.sanalab.sijiusu.core.database.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByMajor_IdAndNameContainingIgnoreCase(Long majorId, String name);

}
