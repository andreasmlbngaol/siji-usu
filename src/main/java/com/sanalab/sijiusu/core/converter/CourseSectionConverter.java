package com.sanalab.sijiusu.core.converter;

import com.sanalab.sijiusu.core.database.model.CourseSection;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminCourseController;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;

public class CourseSectionConverter {
    public static AdminUsersController.CourseSectionTakenDto toDto(CourseSection section) {
        var room = section.getRoom() != null ? section.getRoom().getName() : null;
        var lecturer = section.getLecturer() != null ? section.getLecturer().getName() : null;

        return new AdminUsersController.CourseSectionTakenDto(
            section.getId(),
            section.getCourse().getName(),
            section.getName(),
            room,
            lecturer
        );
    }

    public static AdminCourseController.CourseSectionSumDto toSumDto(CourseSection section) {
        return new AdminCourseController.CourseSectionSumDto(
            section.getId(),
            section.getName(),
            section.getLecturer() != null ? section.getLecturer().getName() : null,
            section.getRoom() != null ? section.getRoom().getName() : null
        );
    }
}
