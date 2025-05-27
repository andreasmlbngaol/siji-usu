package com.sanalab.sijiusu.core.converter;

import com.sanalab.sijiusu.core.database.model.Room;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminRoomController;

public class RoomConverter {
    public static AdminRoomController.RoomDto toDto(Room room) {
        return new AdminRoomController.RoomDto(
            room.getId(),
            room.getName()
        );
    }


}
