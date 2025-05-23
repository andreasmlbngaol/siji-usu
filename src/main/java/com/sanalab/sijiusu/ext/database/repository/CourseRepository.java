package com.sanalab.sijiusu.ext.database.repository;

import com.sanalab.sijiusu.ext.database.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCourseCode(String courseCode);
}
