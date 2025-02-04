package com.verteil.Hospital.Management.System.repository;

import com.verteil.Hospital.Management.System.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room , Integer> {
    Room findFirstByAvailability(String availability);
}
