package com.sanalab.sijiusu.siji_admin.academic.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.academic.service.AdminCourseService;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routing.ADMINS_ACADEMIC)
public class AdminCourseController {
    private final AdminCourseService adminCourseService;

    @Autowired
    public AdminCourseController(AdminCourseService adminCourseService) {
        this.adminCourseService = adminCourseService;
    }

    public record AddCoursePayload(
        @NotNull String name
    ) {
    }

    @PostMapping(Routing.MAJORS + "/{majorId}" + Routing.COURSES)
    @ResponseStatus(HttpStatus.CREATED)
    public void addCourse(
        @Valid @RequestBody AddCoursePayload payload,
        @PathVariable Long majorId
    ) {
        adminCourseService.addCourse(
            payload.name,
            majorId
        );
    }


    public record CourseSectionSumDto(
        Long id,
        String name,
        String lecturer,
        String room
    ) {
    }

    public record CourseDto(
        Long id,
        String name,
        @JsonProperty("course_sections")
        List<CourseSectionSumDto> courseSections
    ) { }

    @GetMapping(Routing.MAJORS + "/{majorId}" + Routing.COURSES)
    public List<CourseDto> getCoursesByMajor(
        @PathVariable Long majorId,
        @RequestParam(required = false, value = "name") String name
    ) {
        if (name != null) {
            return adminCourseService.getCoursesByMajorAndName(majorId, name);
        }
        return adminCourseService.getCoursesByMajor(majorId);
    }

    @GetMapping(Routing.MAJORS + Routing.COURSES + "/{courseId}")
    public CourseDto getCourseById(
        @PathVariable Long courseId
    ) {
        return adminCourseService.getCourseById(courseId);
    }

    public record AddCourseSectionPayload(
        @NotNull String name,
        @JsonProperty("lecturer_id")
        Long lecturerId,
        @JsonProperty("room_id")
        Long roomId
    ) {
    }

    @PostMapping(Routing.COURSES + "/{courseId}" + Routing.SECTIONS)
    @ResponseStatus(HttpStatus.CREATED)
    public void addCourseSection(
        @Valid @RequestBody AddCourseSectionPayload payload,
        @PathVariable Long courseId
    ) {
        adminCourseService.addCourseSection(
            payload.name,
            courseId,
            payload.lecturerId,
            payload.roomId
        );
    }

    public record CourseSumDto(
        Long id,
        String name
    ) { }

    public record CourseSectionDto(
        Long id,
        String name,
        AdminUsersController.LecturerSumDto lecturer,
        AdminRoomController.RoomDto room,
        CourseSumDto course
    ) { }

    @GetMapping(Routing.COURSES + Routing.SECTIONS + "/{sectionId}")
    public CourseSectionDto getCourseSectionById(
        @PathVariable Long sectionId
    ) {
        return adminCourseService.getCourseSectionById(sectionId);
    }
}
