package com.sanalab.sijiusu.siji_student.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;
import com.sanalab.sijiusu.siji_student.service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routing.STUDENTS)
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public AdminUsersController.StudentDto getCurrentStudent() {
        return studentService.getCurrentStudent();
    }

    public record EnrollToSectionPayload(
        @JsonProperty("section_id")
        @NotNull Long sectionId
    ) { }

    @PostMapping(Routing.COURSES + Routing.SECTIONS)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enrollToSection(
        @Valid @RequestBody EnrollToSectionPayload payload
    ) {
        studentService.enrollStudentToSection(payload.sectionId());
    }

    @GetMapping(Routing.COURSES + Routing.SECTIONS)
    public List<AdminUsersController.CourseSectionTakenDto> getAvailableSections(
        @RequestParam(name = "query", required = false, defaultValue = "") String query
    ) {
        return studentService.getAvailableSections(query);
    }
}
