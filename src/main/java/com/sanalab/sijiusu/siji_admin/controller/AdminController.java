package com.sanalab.sijiusu.siji_admin.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.siji_admin.service.AdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public record AddStudentPayload(
        @NotNull String name,
        @Email
        @NotNull String email,
        @NotNull String password,
        @NotNull String nim,
        @JsonProperty("major_id")
        @NotNull Long majorId,
        @JsonProperty("academic_advisor_id")
        @NotNull Long academicAdvisorId
    ) { }

    @PostMapping("/student")
    public void addStudent(
        @Valid @RequestBody AddStudentPayload payload
    ) {
        adminService.addStudent(
            payload.name,
            payload.email,
            payload.password,
            payload.nim,
            payload.majorId,
            payload.academicAdvisorId
        );
    }

    public record AddLecturerPayload(
        @NotNull String name,
        @Email
        @NotNull String email,
        @NotNull String password,
        @NotNull String nip,
        @NotNull String nidn,
        @JsonProperty("department_id")
        @NotNull Long departmentId
    ) { }

    @PostMapping("/lecturer")
    public void addLecturer(
        @Valid @RequestBody AddLecturerPayload payload
    ) {
        adminService.addLecturer(
            payload.name,
            payload.email,
            payload.password,
            payload.nip,
            payload.nidn,
            payload.departmentId
        );
    }

    public record AddFacultyPayload(
        @NotNull String name,

        @Digits(integer = 2, fraction = 0)
        @JsonProperty("faculty_code")
        String facultyCode
    ) { }

    @PostMapping("/faculty")
    public void addFaculty(
        @Valid @RequestBody AddFacultyPayload payload
    ) {
        adminService.addFaculty(
            payload.name,
            payload.facultyCode
        );
    }

    public record AddMajorPayload(
        @NotNull String name,

        @Digits(integer = 2, fraction = 0)
        @JsonProperty("major_code")
        String majorCode
    ) { }

    @PostMapping("/{facultyId}/major")
    public void addMajor(
        @Valid @RequestBody AddMajorPayload payload,
        @PathVariable Long facultyId
    ) {
        adminService.addMajor(
            payload.name,
            facultyId,
            payload.majorCode
        );
    }
}
