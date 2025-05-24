package com.sanalab.sijiusu.siji_admin.users.controller;

import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.users.service.AdminUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routing.ADMINS)
public class AdminController {
    private final AdminUsersService adminUsersService;

    @Autowired
    public AdminController(AdminUsersService adminUsersService) {
        this.adminUsersService = adminUsersService;
    }

    public record AdminDto(
        Long id,
        String name,
        String email,
        String nip
    ) { }

    @GetMapping
    public AdminDto getCurrentlyLoggedInAdmin() {
        return adminUsersService.getCurrentAdmin();
    }
}
