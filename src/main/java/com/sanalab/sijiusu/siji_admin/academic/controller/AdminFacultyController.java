package com.sanalab.sijiusu.siji_admin.academic.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.academic.service.AdminFacultyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routing.ADMINS_ACADEMIC + Routing.FACULTIES)
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFaculty(
        @Valid @RequestBody AddFacultyPayload payload
    ) {
        adminFacultyService.addFaculty(
            payload.name,
            payload.facultyCode
        );
    }

    public record MajorSumDto(
        Long id,
        String name,
        String code
    ) { }

    public record FacultyDto(
        Long id,
        String name,
        String code,
        List<MajorSumDto> departments
    ) { }

    @GetMapping
    public List<FacultyDto> getAllFaculties() {
        return adminFacultyService.getAllFaculties();
    }

    @GetMapping("/{facultyId}")
    public FacultyDto getFacultyById(
        @PathVariable("facultyId") Long facultyId
    ) {
        return adminFacultyService.getFacultyById(facultyId);
    }

    public record UpdateFacultyPayload(
        String name,
        @Digits(integer = 2, fraction = 0)
        @JsonProperty("faculty_code")
        String facultyCode
    ) { }

    @PatchMapping("/{facultyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFaculty(
        @PathVariable("facultyId") Long facultyId,
        @Valid @RequestBody UpdateFacultyPayload payload
    ) {
        adminFacultyService.updateFaculty(
            facultyId,
            payload.name,
            payload.facultyCode
        );
    }
}
