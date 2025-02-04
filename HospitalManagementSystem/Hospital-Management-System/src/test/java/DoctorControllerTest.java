import com.verteil.Hospital.Management.System.controller.DoctorController;
import com.verteil.Hospital.Management.System.model.Doctor;
import com.verteil.Hospital.Management.System.service.DoctorServiceImple;
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
public class DoctorControllerTest {

    @Mock
    private DoctorServiceImple doctorService;

    @InjectMocks
    private DoctorController doctorController;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setDoctorId(1);
        doctor.setFirstName("Dr.John Doe");
        doctor.setSpecialization("Cardiology");
    }


    @Test
    void testGetAllDoctors() {
        List<Doctor> mockDoctors = Arrays.asList(doctor);
        when(doctorService.getAllDoctors()).thenReturn(mockDoctors);

        ResponseEntity<List<Doctor>> response = doctorController.getAllDoctors();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetDoctorById_Found() {
        when(doctorService.getDoctorById(1)).thenReturn(Optional.of(doctor));

        ResponseEntity<Doctor> response = doctorController.getDoctorById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cardiology", response.getBody().getSpecialization());
    }

    @Test
    void testGetDoctorById_NotFound() {
        when(doctorService.getDoctorById(2)).thenReturn(Optional.empty());

        ResponseEntity<Doctor> response = doctorController.getDoctorById(2);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateDoctor() {
        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setDoctorId(1);
        updatedDoctor.setFirstName("Dr. Jane Doe");
        updatedDoctor.setSpecialization("Neurology");

        when(doctorService.updateDoctor(eq(1), any(Doctor.class))).thenReturn(updatedDoctor);

        ResponseEntity<Doctor> response = doctorController.updateDoctor(1, updatedDoctor);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Neurology", response.getBody().getSpecialization());
    }

    @Test
    void testDeleteDoctor() {
        doNothing().when(doctorService).deleteDoctor(1);

        ResponseEntity<String> response = doctorController.deleteDoctor(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Doctor with ID 1 has been deleted successfully.", response.getBody());
    }
}
