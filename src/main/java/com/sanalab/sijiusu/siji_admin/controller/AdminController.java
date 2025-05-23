package com.sanalab.sijiusu.siji_admin.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.siji_admin.service.AdminService;
import com.sanalab.sijiusu.siji_student.service.StudentConverter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String email,
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
        String email,
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

    public record AddRoomPayload(
        @NotNull String name
    ) { }

    @PostMapping("/{departmentId}/room")
    public void addRoom(
        @Valid @RequestBody AddRoomPayload payload,
        @PathVariable Long departmentId
    ) {
        adminService.addRoom(
            payload.name,
            departmentId
        );
    }

    public record AddCoursePayload(
        @NotNull String name,
        @NotNull @JsonProperty("course_code")
        String courseCode
    ) { }

    @PostMapping("/{majorId}/course")
    public void addCourse(
        @Valid @RequestBody AddCoursePayload payload,
        @PathVariable Long majorId
    ) {
        adminService.addCourse(
            payload.name,
            payload.courseCode,
            majorId
        );
    }

    public record AddCourseSectionPayload(
        @NotNull String name,
        @JsonProperty("lecturer_id")
        Long lecturerId,
        @JsonProperty("room_id")
        Long roomId
    ) { }

    @PostMapping("/{courseId}/section")
    public void addCourseSection(
        @Valid @RequestBody AddCourseSectionPayload payload,
        @PathVariable Long courseId
    ) {
        adminService.addCourseSection(
            payload.name,
            courseId,
            payload.lecturerId,
            payload.roomId
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
        LecturerSumDto lecturer
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

    @GetMapping("/users/students")
    public List<StudentDto> getAllStudents() {
        var students = adminService.getAllStudents();
        return students.stream().map(StudentConverter::toDTO).toList();
    }
}
