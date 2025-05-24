package com.sanalab.sijiusu.siji_admin.users.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.users.service.AdminUsersService;
import com.sanalab.sijiusu.siji_student.service.StudentConverter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routing.ADMIN_USERS)
public class AdminUsersController {

    private final AdminUsersService adminUsersService;

    @Autowired
    public AdminUsersController(AdminUsersService adminUsersService) {
        this.adminUsersService = adminUsersService;
    }

    public record AddStudentPayload(
        @NotNull String name,
        @Email
        String email,
        @NotNull String password,
        @NotNull String nim,
        @JsonProperty("major_id")
        @NotNull Long majorId,
        @JsonProperty("academic_advisor_id")
        @NotNull Long academicAdvisorId
    ) { }

    @PostMapping(Routing.STUDENTS)
    public void addStudent(
        @Valid @RequestBody AddStudentPayload payload
    ) {
        adminUsersService.addStudent(
            payload.name,
            payload.email,
            payload.password,
            payload.nim,
            payload.majorId,
            payload.academicAdvisorId
        );
    }

    public record LecturerSumDto(
        Long id,
        String name
    ) { }

    public record CourseSectionTakenDto(
        Long id,
        @JsonProperty("course_name")
        String courseName,
        @JsonProperty("section_name")
        String sectionName,
        String room,
        String lecturer
    ) { }

    public record StudentDto(
        Long id,
        String name,
        String email,
        String nim,
        String faculty,
        String major,
        @JsonProperty("academic_advisor")
        LecturerSumDto academicAdvisor,
        @JsonProperty("courses_taken")
        List<CourseSectionTakenDto> coursesTaken
    ) { }

    @GetMapping(Routing.STUDENTS)
    public List<StudentDto> getAllStudents() {
        var students = adminUsersService.getAllStudents();
        return students.stream().map(StudentConverter::toDTO).toList();
    }

    public record AddLecturerPayload(
        @NotNull String name,
        @Email
        String email,
        @NotNull String password,
        @NotNull String nip,
        @NotNull String nidn,
        @JsonProperty("department_id")
        @NotNull Long departmentId
    ) { }

    @PostMapping(Routing.LECTURERS)
    public void addLecturer(
        @Valid @RequestBody AddLecturerPayload payload
    ) {
        adminUsersService.addLecturer(
            payload.name,
            payload.email,
            payload.password,
            payload.nip,
            payload.nidn,
            payload.departmentId
        );
    }

}
