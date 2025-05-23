package com.sanalab.sijiusu.ext.database.repository;

import com.sanalab.sijiusu.ext.database.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByNameAndDepartmentId(String name, Long departmentId);
}
