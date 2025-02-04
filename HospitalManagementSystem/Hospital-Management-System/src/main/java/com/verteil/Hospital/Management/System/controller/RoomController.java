
package com.verteil.Hospital.Management.System.controller;

import com.verteil.Hospital.Management.System.model.Patient;
import com.verteil.Hospital.Management.System.model.Room;
import com.verteil.Hospital.Management.System.repository.PatientRepository;
import com.verteil.Hospital.Management.System.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepository roomRepository;
    private final PatientRepository patientRepository;

    public RoomController(RoomRepository roomRepository, PatientRepository patientRepository) {
        this.roomRepository = roomRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/get/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Integer roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        return room.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room savedRoom = roomRepository.save(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    @PutMapping("/update/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable Integer roomId, @RequestBody Room updatedRoom) {
        return roomRepository.findById(roomId).map(room -> {
            room.setRoomType(updatedRoom.getRoomType());
            room.setAvailability(updatedRoom.getAvailability());
            Room savedRoom = roomRepository.save(room);
            return new ResponseEntity<>(savedRoom, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Integer roomId) {
        if (roomRepository.existsById(roomId)) {
            roomRepository.deleteById(roomId);
            return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Room not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/bookRoom/{patientId}")
    public ResponseEntity<String> bookRoom(@PathVariable Integer patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        }

        Room availableRoom = roomRepository.findFirstByAvailability("Available");
        if (availableRoom == null) {
            return new ResponseEntity<>("No available rooms", HttpStatus.NOT_FOUND);
        }

        availableRoom.setPatient(patient);
        availableRoom.setAvailability("Occupied");
        roomRepository.save(availableRoom);

        return new ResponseEntity<>("Room booked successfully for patient " + patientId, HttpStatus.OK);
    }
}
