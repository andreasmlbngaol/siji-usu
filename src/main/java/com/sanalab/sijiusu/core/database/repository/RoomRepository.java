package com.sanalab.sijiusu.core.database.repository;

import com.sanalab.sijiusu.core.database.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByNameAndDepartmentId(String name, Long departmentId);
}
