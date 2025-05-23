package com.sanalab.sijiusu.core.converter;

import com.sanalab.sijiusu.core.database.model.CourseSection;
import com.sanalab.sijiusu.siji_admin.controller.AdminController;
import com.sanalab.sijiusu.siji_lecturer.service.LecturerConverter;

public class CourseSectionConverter {
    public static AdminController.CourseSectionTakenDto toDto(CourseSection section) {
        var room = section.getRoom() != null ? section.getRoom().getName() : null;
        var lecturer = section.getLecturer() != null ? LecturerConverter.toSumDto(section.getLecturer()) : null;

        return new AdminController.CourseSectionTakenDto(
            section.getId(),
            section.getCourse().getName(),
            section.getName(),
            room,
            lecturer
        );
    }
}
