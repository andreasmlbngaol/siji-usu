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

}
