package com.sanalab.sijiusu.siji_admin.academic.service;

import com.sanalab.sijiusu.core.database.model.Major;
import com.sanalab.sijiusu.core.database.model.Room;
import com.sanalab.sijiusu.core.database.repository.MajorRepository;
import com.sanalab.sijiusu.core.database.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

}
