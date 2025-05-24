package com.sanalab.sijiusu.siji_lecturer.service;

import com.sanalab.sijiusu.core.converter.CourseSectionConverter;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;
import com.sanalab.sijiusu.siji_lecturer.database.model.Lecturer;
import com.sanalab.sijiusu.siji_student.service.StudentConverter;

public class LecturerConverter {
    public static AdminUsersController.LecturerDto toDto(Lecturer lecturer) {
        var advisedStudents = lecturer.getAdvisedStudents()
            .stream()
            .map(StudentConverter::toSumDto)
            .toList();

        var sections = lecturer.getCourseSections()
            .stream()
            .map(CourseSectionConverter::toDto)
            .toList();

        return new AdminUsersController.LecturerDto(
            lecturer.getId(),
            lecturer.getName(),
            lecturer.getEmail(),
            lecturer.getNip(),
            lecturer.getNidn(),
            lecturer.getDepartment().getFaculty().getName(),
            lecturer.getDepartment().getName(),
            advisedStudents,
            sections
        );
    }

    public static AdminUsersController.LecturerSumDto toSumDto(Lecturer lecturer) {

        return new AdminUsersController.LecturerSumDto(
            lecturer.getId(),
            lecturer.getName()
        );
    }
}
