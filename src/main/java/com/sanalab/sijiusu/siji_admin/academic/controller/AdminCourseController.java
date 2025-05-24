package com.sanalab.sijiusu.siji_admin.academic.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.academic.service.AdminCourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routing.ADMIN_ACADEMIC)
public class AdminCourseController {
    private final AdminCourseService adminCourseService;

    @Autowired
    public AdminCourseController(AdminCourseService adminCourseService) {
        this.adminCourseService = adminCourseService;
    }

    public record AddCoursePayload(
        @NotNull String name
    ) { }

    @PostMapping(Routing.MAJORS + "/{majorId}" + Routing.COURSES)
    public void addCourse(
        @Valid @RequestBody AddCoursePayload payload,
        @PathVariable Long majorId
    ) {
        adminCourseService.addCourse(
            payload.name,
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

    @PostMapping(Routing.COURSES + "/{courseId}" + Routing.SECTIONS)
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

}
