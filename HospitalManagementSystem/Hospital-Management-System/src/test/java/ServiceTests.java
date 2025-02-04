import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.verteil.Hospital.Management.System.model.*;
import com.verteil.Hospital.Management.System.repository.*;
import com.verteil.Hospital.Management.System.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ServiceTests {

    @Mock
    private BillingRepository billingRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @InjectMocks
    private BillingServiceImple billingService;

    private Patient patient;
    private Appointment appointment;
    private Doctor doctor;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setPatientId(1);

        doctor = new Doctor();
        doctor.setSpecialization("Cardiologist");

        appointment = new Appointment();
        appointment.setDoctor(doctor);
    }

    @Test
    void testCalculateTotalBillAmount() {
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findByPatient_PatientId(1)).thenReturn(Arrays.asList(appointment));

        Double billAmount = billingService.calculateTotalBillAmount(1);
        assertEquals(5000.0, billAmount);
    }

    @Mock
    private DoctorRepository doctorRepository;
    @InjectMocks
    private DoctorServiceImple doctorService;

    @Test
    void testCreateDoctor() {
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        Doctor savedDoctor = doctorService.createDoctor(doctor);
        assertNotNull(savedDoctor);
        assertEquals("Cardiologist", savedDoctor.getSpecialization());
    }

    @Test
    void testGetAllDoctors() {
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor));
        List<Doctor> doctors = doctorService.getAllDoctors();
        assertEquals(1, doctors.size());
    }

    @Mock
    private PatientRepository patientRepo;
    @InjectMocks
    private PatientServiceImple patientService;

}
