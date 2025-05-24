package com.sanalab.sijiusu.siji_admin.academic.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.academic.service.AdminFacultyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routing.ADMIN_ACADEMIC)
public class AdminFacultyController {
    private final AdminFacultyService adminFacultyService;

    @Autowired
    public AdminFacultyController(AdminFacultyService adminFacultyService) {
        this.adminFacultyService = adminFacultyService;
    }

    public record AddFacultyPayload(
        @NotNull String name,
        @Digits(integer = 2, fraction = 0)
        @JsonProperty("faculty_code")
        String facultyCode
    ) { }

    @PostMapping(Routing.FACULTIES)
    public void addFaculty(
        @Valid @RequestBody AddFacultyPayload payload
    ) {
        adminFacultyService.addFaculty(
            payload.name,
            payload.facultyCode
        );
    }
}
