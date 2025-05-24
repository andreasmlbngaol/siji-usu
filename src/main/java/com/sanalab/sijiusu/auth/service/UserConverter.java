package com.sanalab.sijiusu.auth.service;

import com.sanalab.sijiusu.auth.database.model.User;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;

public class UserConverter {
    public static AdminUsersController.UserDto toDto(User user) {
        return new AdminUsersController.UserDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole().name()
        );
    }
}
