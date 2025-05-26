package com.sanalab.sijiusu.core.converter;

import com.sanalab.sijiusu.core.database.model.Major;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminFacultyController;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminMajorController;

public class MajorConverter {
    public static AdminMajorController.MajorDto toDto(Major major) {
        var rooms = major.getRooms().stream()
            .map(room ->
                new AdminMajorController.RoomSumDto(
                    room.getId(),
                    room.getName()
                )
            ).toList();

        var faculty = FacultyConverter.toSumDto(major.getFaculty());
        return new AdminMajorController.MajorDto(
            major.getId(),
            major.getName(),
            major.getMajorCode(),
            faculty,
            rooms
        );
    }

    public static AdminFacultyController.MajorSumDto toSumDto(Major major) {
        return new AdminFacultyController.MajorSumDto(
            major.getId(),
            major.getName(),
            major.getMajorCode()
        );
    }
}
