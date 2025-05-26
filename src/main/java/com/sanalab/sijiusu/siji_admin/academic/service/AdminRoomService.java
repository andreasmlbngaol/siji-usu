package com.sanalab.sijiusu.siji_admin.academic.service;

import com.sanalab.sijiusu.core.converter.RoomConverter;
import com.sanalab.sijiusu.core.database.model.Major;
import com.sanalab.sijiusu.core.database.model.Room;
import com.sanalab.sijiusu.core.database.repository.MajorRepository;
import com.sanalab.sijiusu.core.database.repository.RoomRepository;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminRoomController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@Service
public class AdminRoomService {
    private final RoomRepository roomRepository;
    private final MajorRepository majorRepository;

    @Autowired
    public AdminRoomService(RoomRepository roomRepository, MajorRepository majorRepository) {
        this.roomRepository = roomRepository;
        this.majorRepository = majorRepository;
    }

    public void addRoom(String name, Long departmentId) {
        // Validate the payload
        if (name == null || departmentId == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        // Check if the room already exists
        if (roomRepository.existsByNameAndDepartmentId(name, departmentId)) {
            throw responseException(HttpStatus.CONFLICT, "Room already exists");
        }

        // Check if the department exists
        Major department = majorRepository.findById(departmentId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Department not found")
        );

        // Create a new room entity and save it to the database
        var room = new Room();
        room.setName(name);
        room.setDepartment(department);

        roomRepository.save(room);
    }

    public List<AdminRoomController.RoomDto> getRoomsByDepartmentId(Long departmentId) {
        // Check if the department exists
        if (!majorRepository.existsById(departmentId)) {
            throw responseException(HttpStatus.NOT_FOUND, "Department not found");
        }

        // Fetch rooms by department ID and convert to DTOs
        return roomRepository.findAllByDepartmentId(departmentId)
            .stream()
            .map(RoomConverter::toDto)
            .toList();
    }

    public void updateRoom(Long departmentId, Long roomId, String name) {
        if (!majorRepository.existsById(departmentId)) {
            throw responseException(HttpStatus.NOT_FOUND, "Department not found");
        }

        // Fetch the room by ID
        Room room = roomRepository.findById(roomId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Room not found")
        );

        // Update the room name if provided
        if (name != null) {
            if(roomRepository.existsByNameAndDepartmentId(name, departmentId)) {
                throw responseException(HttpStatus.CONFLICT, "Room with this name already exists in the department");
            }
            room.setName(name);
        }

        // Save the updated room entity
        roomRepository.save(room);
    }

}
