package com.sanalab.sijiusu.core.converter;

import com.sanalab.sijiusu.core.database.model.Faculty;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminFacultyController;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminMajorController;

public class FacultyConverter {
    public static AdminFacultyController.FacultyDto toDto(Faculty faculty) {
        var majors = faculty.getDepartments().stream()
            .map(MajorConverter::toSumDto)
            .toList();

        return new AdminFacultyController.FacultyDto(
            faculty.getId(),
            faculty.getName(),
            faculty.getFacultyCode(),
            majors
        );
    }

    public static AdminMajorController.FacultySumDto toSumDto(Faculty faculty) {
        return new AdminMajorController.FacultySumDto(
            faculty.getId(),
            faculty.getName(),
            faculty.getFacultyCode()
        );
    }
}
