package com.sanalab.sijiusu.siji_admin.academic.controller;

import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.academic.service.AdminRoomService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routing.ADMIN_ACADEMIC_DEPARTMENTS)
public class AdminRoomController {
    private final AdminRoomService adminRoomService;

    @Autowired
    public AdminRoomController(AdminRoomService adminRoomService) {
        this.adminRoomService = adminRoomService;
    }

    public record AddRoomPayload(
        @NotNull String name
    ) { }

    @PostMapping("/{departmentId}" + Routing.ROOMS)
    public void addRoom(
        @Valid @RequestBody AddRoomPayload payload,
        @PathVariable Long departmentId
    ) {
        adminRoomService.addRoom(
            payload.name,
            departmentId
        );
    }

}
