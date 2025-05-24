package com.sanalab.sijiusu.siji_student.service;

import com.sanalab.sijiusu.core.converter.CourseSectionConverter;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;
import com.sanalab.sijiusu.siji_lecturer.service.LecturerConverter;
import com.sanalab.sijiusu.siji_student.database.model.Student;

public class StudentConverter {
    public static AdminUsersController.StudentDto toDTO(Student student) {
        var academicAdvisor = LecturerConverter.toSumDto(student.getAcademicAdvisor());
        var sections = student.getCourseSections()
            .stream()
            .map(CourseSectionConverter::toDto)
            .toList();

        return new AdminUsersController.StudentDto(
            student.getId(),
            student.getName(),
            student.getEmail(),
            student.getNim(),
            student.getMajor().getFaculty().getName(),
            student.getMajor().getName(),
            academicAdvisor,
            sections
        );
    }
}
