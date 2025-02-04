import com.verteil.Hospital.Management.System.controller.PatientController;
import com.verteil.Hospital.Management.System.model.Patient;
import com.verteil.Hospital.Management.System.service.PatientServiceImple;
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
class PatientControllerTest {

    @Mock
    private PatientServiceImple patientService;

    @InjectMocks
    private PatientController patientController;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setPatientId(1);
        patient.setFirstName("John");
    }

    @Test
    void testCreatePatient() {
        when(patientService.createPatient(any(Patient.class))).thenReturn(patient);

        ResponseEntity<Patient> response = patientController.createPatient(patient);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John", response.getBody().getFirstName());
    }

    @Test
    void testGetAllPatients() {
        List<Patient> mockPatients = Arrays.asList(patient);
        when(patientService.getAllPatients()).thenReturn(mockPatients);

        ResponseEntity<List<Patient>> response = patientController.getAllPatients();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetPatientById_Found() {
        when(patientService.getPatientById(1)).thenReturn(Optional.of(patient));

        ResponseEntity<Patient> response = patientController.getPatientById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John", response.getBody().getFirstName());
    }


    @Test
    void testUpdatePatient() {
        Patient updatedPatient = new Patient();
        updatedPatient.setPatientId(1);
        updatedPatient.setFirstName("Jane");

        when(patientService.updatePatient(eq(1), any(Patient.class))).thenReturn(updatedPatient);

        ResponseEntity<Patient> response = patientController.updatePatient(1, updatedPatient);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Jane", response.getBody().getFirstName());
    }

    @Test
    void testDeletePatient() {
        doNothing().when(patientService).deletePatient(1);

        ResponseEntity<String> response = patientController.deletePatient(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Patient with ID 1 has been deleted successfully.", response.getBody());
    }
}
