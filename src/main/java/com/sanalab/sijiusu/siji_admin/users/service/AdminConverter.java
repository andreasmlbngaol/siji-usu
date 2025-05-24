package com.sanalab.sijiusu.siji_admin.users.service;

import com.sanalab.sijiusu.siji_admin.database.model.Admin;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminController;

public class AdminConverter {
    public static AdminController.AdminDto toDto(Admin admin) {
        return new AdminController.AdminDto(
            admin.getId(),
            admin.getName(),
            admin.getEmail(),
            admin.getRole().name()
        );
    }
}
