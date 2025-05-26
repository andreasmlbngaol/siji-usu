package com.sanalab.sijiusu.siji_admin.academic.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.academic.service.AdminMajorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routing.ADMINS_ACADEMIC_FACULTIES)
public class AdminMajorController {
    private final AdminMajorService adminMajorService;

    @Autowired
    public AdminMajorController(AdminMajorService adminMajorService) {
        this.adminMajorService = adminMajorService;
    }

    public record AddMajorPayload(
        @NotNull String name,

        @Digits(integer = 2, fraction = 0)
        @JsonProperty("major_code")
        String majorCode
    ) { }

    @PostMapping("/{facultyId}" + Routing.MAJORS)
    @ResponseStatus(HttpStatus.CREATED)
    public void addMajor(
        @Valid @RequestBody AddMajorPayload payload,
        @PathVariable Long facultyId
    ) {
        adminMajorService.addMajor(
            payload.name,
            facultyId,
            payload.majorCode
        );
    }

    public record FacultySumDto(
        Long id,
        String name,
        String code
    ) { }

    public record RoomSumDto(
        Long id,
        String name
    ) { }

    public record MajorDto(
        Long id,
        String name,
        String code,
        FacultySumDto faculty,
        List<RoomSumDto> rooms
    ) { }

    @GetMapping(Routing.MAJORS)
    public List<MajorDto> getAllMajors(
        @RequestParam(value = "name", required = false) String name
    ) {
        if (name != null && !name.isBlank()) {
            return adminMajorService.getMajorsByNameLike(name);
        }
        return adminMajorService.getAllMajors();
    }

    @GetMapping(Routing.MAJORS + "/{majorId}")
    public MajorDto getMajorById(
        @PathVariable("majorId") Long majorId
    ) {
        return adminMajorService.getMajorById(majorId);
    }

    public record UpdateMajorPayload(
        String name,
        @Digits(integer = 2, fraction = 0)
        @JsonProperty("major_code")
        String majorCode
    ) { }

    @PatchMapping(Routing.MAJORS + "/{majorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMajor(
        @PathVariable("majorId") Long majorId,
        @Valid @RequestBody UpdateMajorPayload payload
    ) {
        adminMajorService.updateMajor(
            majorId,
            payload.name,
            payload.majorCode
        );
    }
}
