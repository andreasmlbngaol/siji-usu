package com.sanalab.sijiusu.core.converter;

import com.sanalab.sijiusu.core.database.model.Course;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminCourseController;

public class CourseConverter {
    public static AdminCourseController.CourseDto toDto(Course course) {
        var sections = course.getCourseSections().stream()
            .map(CourseSectionConverter::toSumDto)
            .toList();

        return new AdminCourseController.CourseDto(
            course.getId(),
            course.getName(),
            sections,
            course.getMajor().getId()
        );
    }

    public static AdminCourseController.CourseSumDto toSumDto(Course course) {
        return new AdminCourseController.CourseSumDto(
            course.getId(),
            course.getName(),
            course.getMajor().getId()
        );
    }
}
