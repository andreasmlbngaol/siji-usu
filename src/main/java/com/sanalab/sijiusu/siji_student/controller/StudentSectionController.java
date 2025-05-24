package com.sanalab.sijiusu.siji_student.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_student.service.StudentSectionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routing.STUDENTS)
public class StudentSectionController {

    private final StudentSectionService studentSectionService;

    @Autowired
    public StudentSectionController(StudentSectionService studentSectionService) {
        this.studentSectionService = studentSectionService;
    }

    public record EnrollToSectionPayload(
        @JsonProperty("student_id")
        @NotNull Long studentId,
        @JsonProperty("section_id")
        @NotNull Long sectionId
    ) { }

    @PostMapping(Routing.SECTIONS)
    public void enrollToSection(
        @Valid @RequestBody EnrollToSectionPayload payload
    ) {
        studentSectionService.enrollStudentToSection(
            payload.studentId(),
            payload.sectionId()
        );
    }
}
