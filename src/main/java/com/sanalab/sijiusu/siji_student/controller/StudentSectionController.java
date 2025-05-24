package com.sanalab.sijiusu.siji_student.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;
import com.sanalab.sijiusu.siji_student.service.StudentSectionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routing.STUDENTS)
public class StudentSectionController {

    private final StudentSectionService studentSectionService;

    @Autowired
    public StudentSectionController(StudentSectionService studentSectionService) {
        this.studentSectionService = studentSectionService;
    }

    @GetMapping
    public AdminUsersController.StudentDto getCurrentStudent() {
        return studentSectionService.getCurrentStudent();
    }

    public record EnrollToSectionPayload(
        @JsonProperty("section_id")
        @NotNull Long sectionId
    ) { }

    @PostMapping(Routing.SECTIONS)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enrollToSection(
        @Valid @RequestBody EnrollToSectionPayload payload
    ) {
        studentSectionService.enrollStudentToSection(payload.sectionId());
    }
}
