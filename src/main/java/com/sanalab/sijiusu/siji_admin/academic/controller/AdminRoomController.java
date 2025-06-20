package com.sanalab.sijiusu.siji_admin.academic.controller;

import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.academic.service.AdminRoomService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routing.ADMINS_ACADEMIC_DEPARTMENTS)
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
    @ResponseStatus(HttpStatus.CREATED)
    public void addRoom(
        @Valid @RequestBody AddRoomPayload payload,
        @PathVariable Long departmentId
    ) {
        adminRoomService.addRoom(
            payload.name,
            departmentId
        );
    }

    public record RoomDto(
        Long id,
        String name
    ) { }

    @GetMapping("/{departmentId}" + Routing.ROOMS)
    public List<RoomDto> getRoomsByDepartmentId(
        @PathVariable("departmentId") Long departmentId
    ) {
        return adminRoomService.getRoomsByDepartmentId(departmentId);
    }

    public record UpdateRoomPayload(
        @NotNull String name
    ) { }

    @PatchMapping("/{departmentId}" + Routing.ROOMS + "/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoom(
        @PathVariable("departmentId") Long departmentId,
        @PathVariable("roomId") Long roomId,
        @Valid @RequestBody UpdateRoomPayload roomDto
    ) {
        adminRoomService.updateRoom(
            departmentId,
            roomId,
            roomDto.name()
        );
    }
}
