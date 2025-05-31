package com.sanalab.sijiusu.core.converter;

import com.sanalab.sijiusu.core.database.model.CourseSection;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminCourseController;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminRoomController;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;
import com.sanalab.sijiusu.siji_lecturer.service.LecturerConverter;

public class CourseSectionConverter {
    public static AdminUsersController.CourseSectionTakenDto toSectionTakenDto(CourseSection section) {
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

    public static AdminCourseController.CourseSectionDto toDto(CourseSection section) {
        AdminUsersController.LecturerSumDto lecturer = section.getLecturer() != null ? LecturerConverter.toSumDto(section.getLecturer()) : null;
        AdminRoomController.RoomDto room = section.getRoom() != null ? RoomConverter.toDto(section.getRoom()) : null;
        AdminCourseController.CourseSumDto course = CourseConverter.toSumDto(section.getCourse());

        return new AdminCourseController.CourseSectionDto(
            section.getId(),
            section.getCourse().getName(),
            lecturer,
            room,
            course
        );
    }
}
