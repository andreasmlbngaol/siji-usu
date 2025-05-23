package com.sanalab.sijiusu.core.database.repository;

import com.sanalab.sijiusu.core.database.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCourseCode(String courseCode);
}
