package com.sanalab.sijiusu.siji_lecturer.service;

import com.sanalab.sijiusu.siji_admin.controller.AdminController;
import com.sanalab.sijiusu.siji_lecturer.database.model.Lecturer;

public class LecturerConverter {
    public static AdminController.LecturerSumDto toSumDto(
        Lecturer lecturer
    ) {

        return new AdminController.LecturerSumDto(
            lecturer.getId(),
            lecturer.getName()
        );
    }
}
