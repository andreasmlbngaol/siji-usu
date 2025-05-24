package com.sanalab.sijiusu.siji_lecturer.service;

import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;
import com.sanalab.sijiusu.siji_lecturer.database.model.Lecturer;

public class LecturerConverter {
    public static AdminUsersController.LecturerSumDto toSumDto(
        Lecturer lecturer
    ) {

        return new AdminUsersController.LecturerSumDto(
            lecturer.getId(),
            lecturer.getName()
        );
    }
}
