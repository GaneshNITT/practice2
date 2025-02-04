import com.verteil.Hospital.Management.System.controller.RoomController;
import com.verteil.Hospital.Management.System.model.Appointment;
import com.verteil.Hospital.Management.System.model.Room;
import com.verteil.Hospital.Management.System.repository.RoomRepository;
import com.verteil.Hospital.Management.System.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private RoomController roomController;

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room();
        room.setRoomId(1);
        room.setRoomType("ICU");
        room.setAvailability("Available");
    }

    @Test
    void testGetAllRooms() {
        List<Room> mockRooms = Arrays.asList(room);
        when(roomRepository.findAll()).thenReturn(mockRooms);

        ResponseEntity<List<Room>> response = roomController.getAllRooms();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetRoomById_Found() {
        when(roomRepository.findById(1)).thenReturn(Optional.of(room));

        ResponseEntity<Room> response = roomController.getRoomById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ICU", response.getBody().getRoomType());
    }

    @Test
    void testCreateRoom() {
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        ResponseEntity<Room> response = roomController.createRoom(room);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("ICU", response.getBody().getRoomType());
    }

    @Test
    void testDeleteRoom() {
        when(roomRepository.existsById(1)).thenReturn(true);
        doNothing().when(roomRepository).deleteById(1);

        ResponseEntity<String> response = roomController.deleteRoom(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Room deleted successfully", response.getBody());
    }


}