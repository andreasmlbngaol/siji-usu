package com.sanalab.sijiusu.core.database.repository;

import com.sanalab.sijiusu.core.database.model.CourseSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseSectionRepository extends JpaRepository<CourseSection, Long> {
    List<CourseSection> findAllByCourse_Major_IdAndCourse_NameContainingIgnoreCase(Long majorId, String courseName);

    List<CourseSection> findAllByCourse_Major_Id(Long courseMajorId);

    List<CourseSection> findByNameIgnoreCase(String name);
}
